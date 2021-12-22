package com.rfk.brewco.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rfk.brewco.MainActivity
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    var listPrice: ArrayList<Int> = ArrayList()
    var price = 0

    private var _binding: ActivityCartBinding? = null

    private val binding get() = _binding as ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.viewEmpty.root.visibility = View.VISIBLE

        recyclerView = binding.rvCart
        recyclerView.layoutManager = LinearLayoutManager(this)

        val checkoutFragment = CheckoutFragment()
        val bundle = Bundle()
        val checkout = binding.btnCartCheckout

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        viewModel.cart.observe(this, { cart ->
            if (cart != null) {
                showRecyclerView(cart)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvCart)

        checkout.setOnClickListener {
            if (recyclerView.isNotEmpty()) {
                bundle.putInt("key", price)
                checkoutFragment.arguments = bundle
                checkoutFragment.show(supportFragmentManager, "BottomSheetDialog")
            } else {
                Toast.makeText(this, "Cart cannot be Empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView(cart: PagedList<Cart>) {
        adapter = CartAdapter(this) {
            listPrice.add(it)
            if (listPrice.isNullOrEmpty()) {
                binding.viewEmpty.root.visibility = View.VISIBLE
                binding.tvCartTotalPrice.text = "Rp 0"
            } else {
                val totalPrice = binding.tvCartTotalPrice
                totalPrice.text = StringBuilder("Rp ").append(listPrice.sum().toString())
                price = listPrice.sum()

                binding.viewEmpty.root.visibility = View.GONE
            }
        }
        adapter.submitList(cart)
        recyclerView.adapter = adapter
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipedPosition = viewHolder.adapterPosition
            val cart = adapter.getSwipedData(swipedPosition)
            cart?.let { viewModel.deleteCart(it) }
            listPrice.clear()

            Snackbar.make(
                binding.root,
                "Item Deleted",
                Snackbar.LENGTH_LONG
            ).setAction("Undo") { v ->
                cart?.let { viewModel.insertCart(it) }
                listPrice.clear()
            }.show()
        }
    })

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}