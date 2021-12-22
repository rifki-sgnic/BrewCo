package com.rfk.brewco.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rfk.brewco.MainActivity
import com.rfk.brewco.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null

    private val binding get() = _binding as ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delay: Long = 3000
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}