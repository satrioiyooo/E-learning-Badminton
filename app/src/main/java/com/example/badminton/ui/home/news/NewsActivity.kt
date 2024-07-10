package com.example.badminton.ui.home.news

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.badminton.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var allNewsRV: RecyclerView
    private lateinit var allAdapter: AllAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBarActivityMain: ProgressBar
    private lateinit var allNewsLayout: LinearLayout
    private lateinit var allNewsLayoutManager: LinearLayoutManager

    var pageNum = 1
    var totalAllNews = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeContainer_ActivityMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        allNewsRV = findViewById(R.id.semuaBeritarv)
        swipeRefreshLayout = findViewById(R.id.swipeContainer_ActivityMain)
        allNewsLayout = findViewById(R.id.semuaBeritaLayout)
        progressBarActivityMain = findViewById(R.id.progressBar_ActivityMain)

        // Inisialisasi allNewsLayoutManager
        allNewsLayoutManager = LinearLayoutManager(this)

        hideAll()
        getAllNews()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            pageNum = 1 // Reset page number to 1 when refreshing
            getAllNews()
        }

        allNewsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (totalAllNews > allNewsLayoutManager.itemCount && allNewsLayoutManager.findFirstVisibleItemPosition() >= allNewsLayoutManager.itemCount - 1) {
                    pageNum++
                    getAllNews()
                }
            }
        })
    }

    private fun showAll() {
        progressBarActivityMain.visibility = View.INVISIBLE
        allNewsLayout.visibility = View.VISIBLE
    }

    private fun hideAll() {
        allNewsLayout.visibility = View.INVISIBLE
        progressBarActivityMain.visibility = View.VISIBLE
    }

    private fun getAllNews() {
        hideAll()
        val news = NewsService.newsInstance.geteverything("badminton olympic", pageNum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val allNews = response.body()
                if (allNews != null) {
                    totalAllNews = allNews.totalResults
                    if (!::allAdapter.isInitialized) {
                        allAdapter = AllAdapter(this@NewsActivity)
                        allNewsRV.adapter = allAdapter
                        allNewsRV.layoutManager = allNewsLayoutManager
                    }
                    if (pageNum == 1) {
                        allAdapter.clear()
                    }
                    allAdapter.addAll(allNews.articles)
                    showAll()
                } else {
                    showAll()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Failed Fetching News", t)
                showAll() // Show all even on failure
            }
        })
    }

    companion object {
        const val TAG = "client"
    }
}
