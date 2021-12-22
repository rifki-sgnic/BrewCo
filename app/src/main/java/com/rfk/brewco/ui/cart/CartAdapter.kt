package com.rfk.brewco.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.databinding.ItemMyCartBinding

class CartAdapter(private val context: Context, private val total: (Int) -> Unit) :
    PagedListAdapter<Cart, CartAdapter.CartViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding = ItemMyCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val cart = getItem(position) as Cart
        holder.bind(cart)
    }

    inner class CartViewHolder(private val binding: ItemMyCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var getCart: Cart

        fun bind(cart: Cart) {
            getCart = cart
            with(binding) {
                tvCartCoffeeName.text = cart.name
                tvCartCoffeeDetail.text = "${cart.shot} | ${cart.type} | ${cart.size} | ${cart.ice}"
                tvCartCoffeePrice.text = StringBuilder("Rp ").append(cart.total.toString())
                tvCartCoffeeQty.text = StringBuilder("x ").append(cart.qty.toString())

                when (cart.name) {
                    "Americano" -> ivCoffeeImage.setImageResource(
                        context.resources.getIdentifier(
                            "coffee_americano",
                            "drawable",
                            context.packageName
                        )
                    )
                    "Cappuccino" -> ivCoffeeImage.setImageResource(
                        context.resources.getIdentifier(
                            "coffee_cappuccino",
                            "drawable",
                            context.packageName
                        )
                    )
                    "Mocha" -> ivCoffeeImage.setImageResource(
                        context.resources.getIdentifier(
                            "coffee_mocha",
                            "drawable",
                            context.packageName
                        )
                    )
                    "Flat White" -> ivCoffeeImage.setImageResource(
                        context.resources.getIdentifier(
                            "coffee_flat_white",
                            "drawable",
                            context.packageName
                        )
                    )
                    else -> ivCoffeeImage.setImageResource(
                        context.resources.getIdentifier(
                            "coffee_americano",
                            "drawable",
                            context.packageName
                        )
                    )
                }

                total(cart.total)
            }
        }
    }

    fun getSwipedData(swipedPos: Int): Cart? = getItem(swipedPos)

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