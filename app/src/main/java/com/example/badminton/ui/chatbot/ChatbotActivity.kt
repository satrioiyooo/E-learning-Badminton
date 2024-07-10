package com.example.badminton.ui.chatbot

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.badminton.databinding.ActivityChatbotBinding
import kotlinx.coroutines.launch

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupRecyclerView()
        setupMessageInput()

        lifecycleScope.launch {
            chatViewModel.messageList.collect { messages ->
                messageAdapter.submitList(messages)
                binding.messageList.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        binding.messageList.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            adapter = messageAdapter
        }
    }

    private fun setupMessageInput() {
        binding.sendButton.setOnClickListener {
            val message = binding.messageEditText.text.toString()
            if (message.isNotEmpty()) {
                chatViewModel.sendMessage(message)
                binding.messageEditText.text.clear()
            }
        }
    }
}
