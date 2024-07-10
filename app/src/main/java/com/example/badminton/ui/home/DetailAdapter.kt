package com.example.badminton.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.badminton.R

class DetailAdapter(private val listKursus: ArrayList<Kursus>) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivItemPhoto: ImageView = itemView.findViewById(R.id.image)
        val tvItemTitle: TextView = itemView.findViewById(R.id.title)
        init {
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val kursus = listKursus[position]
        holder.ivItemPhoto.setImageResource(kursus.photo)
        holder.tvItemTitle.text = kursus.name
    }

    override fun getItemCount(): Int {
        return listKursus.size
    }
}
