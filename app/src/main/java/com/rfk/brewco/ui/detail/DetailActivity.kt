package com.rfk.brewco.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rfk.brewco.R
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.databinding.ActivityDetailBinding
import com.rfk.brewco.databinding.FragmentHomeBinding
import com.rfk.brewco.ui.cart.CartActivity
import com.rfk.brewco.utils.COFFEE_ID

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: ActivityDetailBinding? = null

    private val binding get() = _binding as ActivityDetailBinding

    private var price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            finish()
        }

        val cart = binding.ibCart
        cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val coffeeId = intent.getIntExtra(COFFEE_ID, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.setSelectedId(coffeeId)
        viewModel.getCoffee().observe(this, { coffee ->
            if (coffee != null) {
                binding.ivDetailCoffeeImage.setImageResource(resources.getIdentifier(coffee.imagePath, "drawable", packageName))
                binding.tvDetailCoffeeName.text = coffee.name
                binding.tvDetailTotal.text = coffee.price.toString()
                price = coffee.price
            }
        })

        val qty = binding.tvDetailCoffeeQty
        var num = qty.text.toString().toInt()



        val addQty = binding.ibAddQty
        addQty.setOnClickListener {
            num++
            qty.text = num.toString()
            calculateTotalPrice(num, price)
        }
        val removeQty = binding.ibRemoveQty
        removeQty.setOnClickListener {
            if (num > 0) {
                num--
                qty.text = num.toString()
                calculateTotalPrice(num, price)
            }
        }

        val rgShot = binding.rgShot
        rgShot.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_single_shot -> Toast.makeText(this, "Single", Toast.LENGTH_SHORT).show()
                R.id.rb_double_shot -> Toast.makeText(this, "Double", Toast.LENGTH_SHORT).show()
            }
        }

        val rgSelect = binding.rgSelect
        rgSelect.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_select_hot -> Toast.makeText(this, "Hot", Toast.LENGTH_SHORT).show()
                R.id.rb_select_ice -> Toast.makeText(this, "Ice", Toast.LENGTH_SHORT).show()
            }
        }

        val rgSize = binding.rgSize
        rgSize.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_size_small -> Toast.makeText(this, "Small", Toast.LENGTH_SHORT).show()
                R.id.rb_size_regular -> Toast.makeText(this, "Regular", Toast.LENGTH_SHORT).show()
                R.id.rb_size_large -> Toast.makeText(this, "Large", Toast.LENGTH_SHORT).show()
            }
        }

        val rgIce = binding.rgIce
        rgIce.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_ice_less -> Toast.makeText(this, "Less", Toast.LENGTH_SHORT).show()
                R.id.rb_ice_normal -> Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
                R.id.rb_ice_more -> Toast.makeText(this, "More", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateTotalPrice(num: Int, price: Int) {
        var total = 0
        total = num * price
        binding.tvDetailTotal.text = total.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}