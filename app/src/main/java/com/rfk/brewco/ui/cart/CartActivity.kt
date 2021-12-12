package com.rfk.brewco.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rfk.brewco.R
import com.rfk.brewco.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private var _binding: ActivityCartBinding? = null

    private val binding get() = _binding as ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            finish()
        }
    }
}