package com.rfk.brewco.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun calculateTotalPrice(num: Int, price: Int) {
        var total = 0
        total = num * price
        binding.tvDetailTotal.text = total.toString()
    }

    fun onShotChecked(view: android.view.View) {
        val rg = binding.rgShot
        when (rg.checkedRadioButtonId) {
            R.id.rb_single_shot -> {
                Toast.makeText(this, "Single", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_double_shot -> Toast.makeText(this, "Double", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSelectChecked(view: android.view.View) {
        val rg = binding.rgSelect
        val rb = findViewById<RadioButton>(rg.checkedRadioButtonId)
    }

    fun onSizeChecked(view: android.view.View) {}
    fun onIceChecked(view: android.view.View) {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}