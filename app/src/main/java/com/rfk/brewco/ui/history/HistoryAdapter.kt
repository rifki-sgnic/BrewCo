package com.rfk.brewco.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.data.history.History
import com.rfk.brewco.databinding.ItemHistoryBinding

class HistoryAdapter(private val context: Context) :
    PagedListAdapter<History, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position) as History
        holder.bind(history)
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var getHistory: History

        fun bind(history: History) {
            getHistory = history
            with(binding) {
                tvHistoryName.text = history.name
                tvHistoryQty.text = StringBuilder("x ").append(history.qty.toString())
                tvHistoryAddress.text = history.address
                tvHistoryPrice.text = StringBuilder("Rp ").append(history.total.toString())
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}