package com.rfk.brewco.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.R
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.databinding.ActivityCartBinding
import com.rfk.brewco.ui.detail.DetailViewModel
import com.rfk.brewco.ui.home.CoffeeAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CartViewModel
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

        recyclerView = binding.rvCart
        recyclerView.layoutManager = LinearLayoutManager(this)

        val checkoutFragment = CheckoutFragment()
        val checkout = binding.btnCartCheckout

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        viewModel.cart.observe(this, Observer(this::showRecyclerView))

        checkout.setOnClickListener {
            checkoutFragment.show(supportFragmentManager, "BottomSheetDialog")
        }
    }

    private fun showRecyclerView(cart: PagedList<Cart>) {
        val adapter = CartAdapter(this)
        adapter.submitList(cart)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}