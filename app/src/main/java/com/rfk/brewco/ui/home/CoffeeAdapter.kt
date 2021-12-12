package com.rfk.brewco.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.databinding.ItemCoffeeBinding
import com.rfk.brewco.ui.detail.DetailActivity
import com.rfk.brewco.utils.COFFEE_ID

class CoffeeAdapter(private val context: Context) : PagedListAdapter<Coffee, CoffeeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val coffee = getItem(position) as Coffee
        holder.bind(coffee)
    }

    inner class ListViewHolder(private val binding: ItemCoffeeBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var getCoffee: Coffee

        fun bind(coffee: Coffee) {
            getCoffee = coffee
            with(binding) {
                tvCoffeeName.text = coffee.name
                ivCoffeeImage.setImageResource(context.resources.getIdentifier(coffee.imagePath, "drawable", context.packageName))
                cardView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(COFFEE_ID, coffee.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Coffee>() {
            override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
                return oldItem == newItem
            }
        }
    }
}