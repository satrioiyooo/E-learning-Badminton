package com.example.badminton.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.badminton.R
import com.example.badminton.ui.home.news.NewsActivity
import com.example.badminton.ui.home.ujian.WelcomeUjianActivity

class HomeFragment : Fragment() {
    private lateinit var rvDetail: RecyclerView
    private val list = ArrayList<Kursus>()
    private lateinit var button4: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDetail = view.findViewById(R.id.rvArticle)
        rvDetail.setHasFixedSize(true)
        button4 = view.findViewById(R.id.button4)
        button4.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivity::class.java)
            startActivity(intent)
        }
        if (list.isEmpty()) {
            list.addAll(getListArticle())
        }
        showRecyclerList()
    }
    private fun getListArticle(): ArrayList<Kursus> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listArticle = ArrayList<Kursus>()
        val videoUrls = resources.getStringArray(R.array.video)
        for (i in dataName.indices) {
            val article = Kursus(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1), videoUrls.getOrNull(i) ?: "")
            listArticle.add(article)
        }
        dataPhoto.recycle()
        return listArticle
    }
    private fun showRecyclerList() {
        rvDetail.layoutManager = LinearLayoutManager(requireContext())
        val listArticleAdapter = HomeAdapter(list)
        rvDetail.adapter = listArticleAdapter
        listArticleAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int) {
                if (position == 3) {
                    showUjianPage()
                } else {
                    showSelectedArticle(list[position], position)
                }
            }
        })
    }
    private fun showSelectedArticle(kursus: Kursus, position: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("Kursus", kursus)
        intent.putExtra("position", position)
        startActivity(intent)
    }
    private fun showUjianPage() {
        val intent = Intent(requireContext(), WelcomeUjianActivity::class.java)
        startActivity(intent)
    }
}
