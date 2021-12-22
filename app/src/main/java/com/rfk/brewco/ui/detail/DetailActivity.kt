package com.rfk.brewco.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rfk.brewco.R
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.databinding.ActivityDetailBinding
import com.rfk.brewco.ui.cart.CartActivity
import com.rfk.brewco.utils.COFFEE_ID

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: ActivityDetailBinding? = null

    private val binding get() = _binding as ActivityDetailBinding

    private var price: Int = 0
    private var shot: String = ""
    private var type: String = ""
    private var size: String = ""
    private var ice: String = ""
    private var total: Int = 0

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

        val qty = binding.tvDetailCoffeeQty
        var num = qty.text.toString().toInt()

        viewModel.getCoffee().observe(this, { coffee ->
            if (coffee != null) {
                binding.ivDetailCoffeeImage.setImageResource(
                    resources.getIdentifier(
                        coffee.imagePath,
                        "drawable",
                        packageName
                    )
                )
                binding.tvDetailCoffeeName.text = coffee.name
                binding.tvDetailTotal.text = coffee.price.toString()
                price = coffee.price
                viewModel.calculateTotalPrice(num, price)
                viewModel.calculate()
            }
        })

        val addQty = binding.ibAddQty
        addQty.setOnClickListener {
            num++
            qty.text = num.toString()
            viewModel.calculateTotalPrice(num, price)
            viewModel.calculate()
        }
        val removeQty = binding.ibRemoveQty
        removeQty.setOnClickListener {
            if (num > 1) {
                num--
                qty.text = num.toString()
                viewModel.calculateTotalPrice(num, price)
                viewModel.calculate()
            }
        }

        val rgShot = binding.rgShot
        rgShot.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_single_shot -> {
                    val rbShot = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    shot = rbShot.text.toString()
                    viewModel.currentShot(0)
                    viewModel.calculate()
                }
                R.id.rb_double_shot -> {
                    val rbShot = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    shot = rbShot.text.toString()
                    viewModel.currentShot(3000)
                    viewModel.calculate()
                }
            }
        }

        val rgSelect = binding.rgSelect
        rgSelect.setOnCheckedChangeListener { radioGroup, i ->
            val rbSelect = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            type = rbSelect.text.toString()
            when (i) {
                R.id.rb_select_hot -> {
                    isHotSelected(true)
                    type = rbSelect.text.toString()
                    viewModel.currentType(0)
                    viewModel.calculate()
                }
                R.id.rb_select_ice -> {
                    isHotSelected(false)
                    type = rbSelect.text.toString()
                    viewModel.currentType(3000)
                    viewModel.calculate()
                }
            }
        }

        val rgSize = binding.rgSize
        rgSize.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_size_small -> {
                    val rbSize = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    size = rbSize.text.toString()
                    viewModel.currentSize(0)
                    viewModel.calculate()
                }
                R.id.rb_size_regular -> {
                    val rbSize = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    size = rbSize.text.toString()
                    viewModel.currentSize(2000)
                    viewModel.calculate()
                }
                R.id.rb_size_large -> {
                    val rbSize = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                    size = rbSize.text.toString()
                    viewModel.currentSize(5000)
                    viewModel.calculate()
                }
            }
        }

        viewModel.total.observe(this, {
            binding.tvDetailTotal.text = it.toString()
            total = it
        })

        val addToCart = binding.btnAddToCart
        addToCart.setOnClickListener {
            if (shot.isNotEmpty() && type.isNotEmpty() && type.isNotEmpty() && ice.isNotEmpty() && size.isNotEmpty()) {

                val name = binding.tvDetailCoffeeName.text
                val cartQty = qty.text
                val selectedShot = shot
                val selectedType = type
                val selectedIce = ice
                val selectedSize = size
                val totalPrice = total

                val cart = Cart(
                    name = name.toString(),
                    qty = cartQty.toString().toInt(),
                    shot = selectedShot,
                    type = selectedType,
                    size = selectedSize,
                    ice = selectedIce,
                    total = totalPrice
                )
                viewModel.insertCart(cart)

                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Pick all the selection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isHotSelected(state: Boolean) {
        val rgIce = binding.rgIce
        if (state) {
            rgIce.clearCheck()
            for (index in 0 until rgIce.childCount) {
                rgIce.getChildAt(index).isEnabled = false
            }
            ice = "-"
        } else {
            for (index in 0 until rgIce.childCount) {
                rgIce.getChildAt(index).isEnabled = true
            }
            rgIce.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    R.id.rb_ice_less -> {
                        val rbIce = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                        ice = rbIce.text.toString()
                    }
                    R.id.rb_ice_normal -> {
                        val rbIce = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                        ice = rbIce.text.toString()
                    }
                    R.id.rb_ice_more -> {
                        val rbIce = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                        ice = rbIce.text.toString()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}