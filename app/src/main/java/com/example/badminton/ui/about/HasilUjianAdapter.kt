package com.example.badminton.ui.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.badminton.databinding.ItemAboutBinding
import com.example.badminton.ui.data.HasilUjian

class HasilUjianAdapter(private val hasilList: List<HasilUjian>) :
    RecyclerView.Adapter<HasilUjianAdapter.HasilUjianViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HasilUjianViewHolder {
        val binding = ItemAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HasilUjianViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HasilUjianViewHolder, position: Int) {
        val hasil = hasilList[position]
        holder.bind(hasil)
    }
    override fun getItemCount(): Int {
        return hasilList.size
    }
    class HasilUjianViewHolder(private val binding: ItemAboutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hasilUjian: HasilUjian) {
            binding.tvNilai.text = "Nilai: ${hasilUjian.nilai}"
            binding.tvHasil.text = hasilUjian.hasil
        }
    }
}
