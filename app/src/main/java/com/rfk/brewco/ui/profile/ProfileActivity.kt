package com.rfk.brewco.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.databinding.ActivityProfileBinding
import com.rfk.brewco.ui.detail.DetailViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private var _binding: ActivityProfileBinding? = null

    private val binding get() = _binding as ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            finish()
        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
//        viewModel.setSelectedId(1)
//
//        viewModel.getUser().observe(this, { user ->
//            if (user != null) {
//                binding.tvProfileName.text = user.name
//                binding.tvProfilePhone.text = user.phoneNumber
//                binding.tvProfileEmail.text = user.email
//                binding.tvProfileAddress.text = user.address
//            }
//        })

    }
}