package com.example.badminton.ui.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ChatViewModel : ViewModel() {

    private val _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    val messageList: StateFlow<List<MessageModel>> get() = _messageList

    private val client: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = Level.BODY
        }
        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    fun sendMessage(question: String) {
        viewModelScope.launch {
            val updatedList = _messageList.value.toMutableList()
            updatedList.add(MessageModel(question, "user"))
            _messageList.value = updatedList
            try {
                val response = generateContent(question)
                updatedList.add(MessageModel(response, "model"))
                _messageList.value = updatedList
            } catch (e: Exception) {
                updatedList.add(MessageModel("Error: ${e.message}", "model"))
                _messageList.value = updatedList
            }
        }
    }

    private suspend fun generateContent(question: String): String {
        return withContext(Dispatchers.IO) {
            val json = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", question)
                            })
                        })
                    })
                })
            }

            val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${Constants.apiKey}")
                .post(requestBody)
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body?.string()
                    val responseJson = JSONObject(responseBody ?: "")
                    val candidates = responseJson.getJSONArray("candidates")
                    if (candidates.length() > 0) {
                        val content = candidates.getJSONObject(0).getJSONObject("content")
                        val parts = content.getJSONArray("parts")
                        if (parts.length() > 0) {
                            parts.getJSONObject(0).getString("text")
                        } else {
                            "No response text found"
                        }
                    } else {
                        "No candidates found"
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                "Failed to get response"
            }
        }
    }
}
