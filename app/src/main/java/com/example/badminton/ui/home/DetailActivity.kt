// DetailActivity.kt
package com.example.badminton.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.badminton.R
import com.example.badminton.ui.home.kursus.DetailKursusActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var rvDetail: RecyclerView
    private val list = ArrayList<Kursus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val kursus = intent.getParcelableExtra<Kursus>("Kursus")
        val position = intent.getIntExtra("position", -1)

        if (kursus != null) {
            val textView = findViewById<TextView>(R.id.tvJudulArtc)
            val imageView = findViewById<ImageView>(R.id.ivArtc)
            textView.text = kursus.name
            imageView.setImageResource(kursus.photo)
        }

        if (position != -1) {
            val dataName = resources.getStringArray(R.array.course_names)
            val dataDescription = resources.getStringArray(R.array.course_descriptions)
            val dataPhoto = resources.obtainTypedArray(R.array.course_photos)
            val videoUrls = resources.getStringArray(R.array.video) // Ambil URL video dari resources

            val startIndex = position * 5 // 5 items per section
            val endIndex = minOf(startIndex + 5, dataName.size) // Ensure endIndex doesn't exceed array size

            for (i in startIndex until endIndex) {
                list.add(Kursus(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1), videoUrls.getOrNull(i) ?: ""))
            }
            dataPhoto.recycle() // Make sure to recycle TypedArray after use
        }

        rvDetail = findViewById(R.id.rvDetail)
        rvDetail.setHasFixedSize(true)
        rvDetail.layoutManager = LinearLayoutManager(this)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val detailAdapter = DetailAdapter(list)
        rvDetail.adapter = detailAdapter

        detailAdapter.setOnItemClickCallback(object : DetailAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int) {
                showSelectedKursus(list[position])
            }
        })
    }

    private fun showSelectedKursus(kursus: Kursus) {
        val intent = Intent(this, DetailKursusActivity::class.java)
        intent.putExtra("Kursus", kursus)
        startActivity(intent)
    }
}

