package com.example.badminton.ui.home.ujian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.badminton.MainActivity
import com.example.badminton.R
import com.example.badminton.ui.data.HasilUjian
import kotlinx.coroutines.launch

class HasilUjianActivity : AppCompatActivity() {
    private lateinit var tvNilai: TextView
    private lateinit var tvMessage: TextView
    private lateinit var tvHasil: TextView
    private lateinit var btnUlang: Button
    private lateinit var hasilUjianDatabase: HasilUjianDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_ujian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvNilai = findViewById(R.id.tvNilai)
        tvMessage = findViewById(R.id.tvMessage)
        btnUlang = findViewById(R.id.btnUlang)
        tvHasil = findViewById(R.id.tvhasil)

        hasilUjianDatabase = HasilUjianDatabase.getDatabase(this)

        val nilai = intent.extras!!.getInt("nilai")
        val benar = intent.extras!!.getInt("benar")
        val salah = intent.extras!!.getInt("salah")

        tvMessage.text = "Jawaban Benar: $benar , Jawaban Salah: $salah"
        tvNilai.text = nilai.toString()

        val hasil: String = when {
            nilai == 100 -> "Kamu Mendapatkan Nilai A, Kamu Keren!!"
            nilai >= 80 -> "Kamu Mendapatkan Nilai B, Kamu Keren!!"
            nilai >= 60 -> "Kamu Mendapatkan Nilai C, Semangat Terus!!"
            nilai >= 40 -> "Kamu Mendapatkan Nilai D, Semangat Terus!!"
            nilai >= 20 -> "Kamu Mendapatkan Nilai E, Semangat Terus!!"
            else -> "Kamu Mendapatkan Nilai E, Semangat Terus!!"
        }

        tvHasil.text = hasil

        saveHasilUjian(nilai, hasil)

        btnUlang.setOnClickListener {
            finish()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveHasilUjian(nilai: Int, hasil: String) {
        val hasilUjian = HasilUjian(nilai = nilai, hasil = hasil)
        lifecycleScope.launch {
            hasilUjianDatabase.hasilUjianDao().insert(hasilUjian)
        }
    }
}
