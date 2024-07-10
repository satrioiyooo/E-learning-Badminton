package com.example.badminton.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.badminton.R

class HomeAdapter(private val listArticle: ArrayList<Kursus>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.image)
        val tvName: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kursus = listArticle[position]

        // Load scaled bitmap
        val resources = holder.itemView.context.resources
        val bitmap = ImageUtils.decodeSampledBitmapFromResource(resources, kursus.photo, 100, 100) // Adjust the dimensions as needed
        holder.imgPhoto.setImageBitmap(bitmap)

        holder.tvName.text = kursus.name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }
}
