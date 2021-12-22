package com.rfk.brewco.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.history.History
import com.rfk.brewco.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HistoryViewModel

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding as FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewEmptyHistory.root.visibility = View.VISIBLE

        recyclerView = binding.rvHistory
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)[HistoryViewModel::class.java]

        viewModel.history.observe(viewLifecycleOwner, { history ->
            if (history != null) {
                showRecyclerView(history)
            }
        })
    }

    private fun showRecyclerView(history: PagedList<History>) {
        val adapter = HistoryAdapter(requireContext())
        adapter.submitList(history)
        recyclerView.adapter = adapter
        binding.viewEmptyHistory.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}