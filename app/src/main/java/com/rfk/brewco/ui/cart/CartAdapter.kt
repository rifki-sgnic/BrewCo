package com.rfk.brewco.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.databinding.ItemMyCartBinding

class CartAdapter(private val context: Context) : PagedListAdapter<Cart, CartAdapter.ListViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ListViewHolder {
        val binding = ItemMyCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ListViewHolder, position: Int) {
        val cart = getItem(position) as Cart
        holder.bind(cart)
    }

    inner class ListViewHolder(private val binding: ItemMyCartBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var getCart: Cart

        fun bind(cart: Cart) {
            getCart = cart
            with(binding) {
                tvCartCoffeeName.text = cart.name
                tvCartCoffeeDetail.text = "${cart.shot} | ${cart.type} | ${cart.size} | ${cart.ice}"
                tvCartCoffeePrice.text = "Rp ${cart.total}"
                tvCartCoffeeQty.text = "x ${cart.qty}"
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }
}