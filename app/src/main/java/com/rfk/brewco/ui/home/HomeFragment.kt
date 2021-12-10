package com.rfk.brewco.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.R
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_coffee)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.coffee.observe(viewLifecycleOwner, Observer(this::showRecyclerView))
    }

    private fun showRecyclerView(coffee: PagedList<Coffee>) {
        val adapter = CoffeeAdapter(requireContext())
        adapter.submitList(coffee)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}