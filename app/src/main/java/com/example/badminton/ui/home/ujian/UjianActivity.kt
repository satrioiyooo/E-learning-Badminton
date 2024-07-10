package com.example.badminton.ui.home.ujian

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.badminton.R

class UjianActivity : AppCompatActivity() {
    private lateinit var tvSoal: TextView
    private lateinit var tvTime: TextView
    private lateinit var btnSelanjutnya: Button
    private lateinit var rgPilihan: RadioGroup
    private lateinit var rbA: RadioButton
    private lateinit var rbB: RadioButton
    private lateinit var rbC: RadioButton
    private lateinit var rbD: RadioButton
    private var nomor = 0
    private var score = 0
    private var benar = 0
    private var salah = 0



    private val soal = arrayOf(
        "1. Teknik pegangan raket yang benar pada bulu tangkis adalah?",
        "2. Mengapa footwork yang baik penting dalam bulu tangkis?",
        "3. Pukulan forehand biasanya dilakukan dari posisi?",
        "4. Jenis servis yang membuat shuttlecock jatuh dekat net disebut?",
        "5. Apa tujuan utama dari net shot dalam bulu tangkis?",
        "6. Pukulan clear bertujuan untuk?",
        "7. Drop shot digunakan untuk?",
        "8. Smash adalah pukulan yang?",
        "9. Pukulan drive biasanya digunakan dalam permainan?",
        "10. Teknik defensif yang digunakan untuk mengembalikan smash disebut?",
        "11. Langkah chasse digunakan untuk?",
        "12. Net kill dilakukan untuk?",
        "13. Keuntungan dari jump smash adalah?",
        "14. Pukulan slice digunakan untuk?",
        "15. Flick serve biasanya digunakan untuk?"
    )

    private val option = arrayOf(
        "Memegang raket dengan tangan terbuka", "Menggenggam raket dengan kuat", "Menggunakan forehand grip untuk semua jenis pukulan", "Menggenggam raket dengan kendur",
        "Untuk menambah kekuatan pukulan", "Untuk menghemat energi", "Untuk memastikan pemain dapat mencapai shuttlecock dengan cepat", "Untuk mengurangi keringat",
        "Sisi raket", "Sisi non-raket", "Depan net", "Tengah lapangan",
        "Servis panjang", "Servis pendek", "Flick serve", "Smash serve",
        "Mengirim shuttlecock ke belakang lapangan", "Mencetak poin langsung", "Mengecoh lawan dengan pukulan halus dekat net", "Mengulur waktu",
        "Mengirim shuttlecock ke bagian depan lapangan lawan", "Mengirim shuttlecock ke bagian belakang lapangan lawan", "Menjatuhkan shuttlecock dekat net", "Mematikan shuttlecock dengan cepat",
        "Membuat lawan bergerak mundur", "Membuat lawan bergerak cepat ke depan lapangan", "Membuat lawan kelelahan", "Mengakhiri rally dengan smash",
        "Dilakukan dengan halus", "Dilakukan dengan keras dan diarahkan ke bawah", "Dilakukan dengan lambat", "Dilakukan dengan mengayunkan raket dari bawah ke atas",
        "Tunggal", "Ganda", "Campuran", "Junior",
        "Block", "Clear", "Drop shot", "Net shot",
        "Bergerak maju dan mundur", "Bergerak cepat ke samping", "Melakukan smash", "Melakukan servis",
        "Mengakhiri rally dengan cepat di dekat net", "Mengirim shuttlecock ke bagian belakang lapangan", "Mengecoh lawan dengan pukulan halus", "Memulai permainan",
        "Menghemat energi", "Memberikan sudut pukulan yang lebih tajam dan kekuatan yang lebih besar", "Mengurangi risiko cedera", "Memudahkan gerakan footwork",
        "Menambah kecepatan shuttlecock", "Mengubah arah shuttlecock secara tiba-tiba", "Membuat shuttlecock tetap lurus", "Mengurangi putaran shuttlecock",
        "Mengirim shuttlecock rendah di dekat net", "Mengirim shuttlecock tinggi dan jauh ke belakang lapangan", "Mengakhiri rally dengan cepat", "Mengecoh lawan di dekat net"
    )

    private val jawaban = arrayOf(
        "Menggenggam raket dengan kuat",
        "Untuk memastikan pemain dapat mencapai shuttlecock dengan cepat",
        "Sisi raket",
        "Servis pendek",
        "Mengecoh lawan dengan pukulan halus dekat net",
        "Mengirim shuttlecock ke bagian belakang lapangan lawan",
        "Membuat lawan bergerak cepat ke depan lapangan",
        "Dilakukan dengan keras dan diarahkan ke bawah",
        "Ganda",
        "Block",
        "Bergerak cepat ke samping",
        "Mengakhiri rally dengan cepat di dekat net",
        "Memberikan sudut pukulan yang lebih tajam dan kekuatan yang lebih besar",
        "Mengubah arah shuttlecock secara tiba-tiba",
        "Mengirim shuttlecock tinggi dan jauh ke belakang lapangan"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ujian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        tvSoal = findViewById(R.id.tvSoal)
        tvTime = findViewById(R.id.tvTime)
        btnSelanjutnya = findViewById(R.id.btnSelanjutnya)
        rgPilihan = findViewById(R.id.rgPilihan)
        rbA = findViewById(R.id.rbA)
        rbB = findViewById(R.id.rbB)
        rbC = findViewById(R.id.rbC)
        rbD = findViewById(R.id.rbD)

        rgPilihan.check(0)

        updateQuestion()

        object : CountDownTimer(600000, 1000) { // 10 menit = 600000 ms
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                tvTime.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                tvTime.text = "Waktu Habis!!"
                score = (benar.toDouble() / soal.size * 100).toInt()
                val next = Intent(applicationContext, HasilUjianActivity::class.java)
                next.putExtra("nilai", score)
                next.putExtra("benar", benar)
                next.putExtra("salah", salah)
                startActivity(next)
                finish() // Finish UjianActivity
            }
        }.start()

        btnSelanjutnya.setOnClickListener {
            if (rgPilihan.checkedRadioButtonId != -1) {
                val pilihanUser = findViewById<RadioButton>(rgPilihan.checkedRadioButtonId)
                val jawabanUser = pilihanUser.text.toString()
                rgPilihan.clearCheck()

                if (jawabanUser.equals(jawaban[nomor], ignoreCase = true)) {
                    benar++
                } else {
                    salah++
                }
                nomor++
                if (nomor < soal.size) {
                    updateQuestion()
                } else {
                    score = (benar.toDouble() / soal.size * 100).toInt()
                    val next = Intent(applicationContext, HasilUjianActivity::class.java)
                    next.putExtra("nilai", score)
                    next.putExtra("benar", benar)
                    next.putExtra("salah", salah)
                    startActivity(next)
                    finish() // Finish UjianActivity
                }
            } else {
                Toast.makeText(this@UjianActivity, "Silahkan Pilih Jawaban Terlebih Dahulu!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateQuestion() {
        tvSoal.text = soal[nomor]
        rbA.text = option[nomor * 4]
        rbB.text = option[nomor * 4 + 1]
        rbC.text = option[nomor * 4 + 2]
        rbD.text = option[nomor * 4 + 3]
    }
}
