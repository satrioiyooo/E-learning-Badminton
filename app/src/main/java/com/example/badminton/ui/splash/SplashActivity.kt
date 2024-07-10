package com.example.badminton.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.badminton.MainActivity
import com.example.badminton.R
import com.example.badminton.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            setContentView(R.layout.activity_splash)
            Handler(Looper.getMainLooper()).postDelayed({
                goToMainActivity()
            }, 2000L)
        } else {
            setContentView(R.layout.activity_splash)
            Handler(Looper.getMainLooper()).postDelayed({
                goToLoginActivity()
            }, 2000L)
        }
    }
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
