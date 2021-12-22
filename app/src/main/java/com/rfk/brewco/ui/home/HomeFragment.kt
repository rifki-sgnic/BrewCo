package com.rfk.brewco.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rfk.brewco.UserPreference
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.Coffee
import com.rfk.brewco.data.user.User
import com.rfk.brewco.databinding.FragmentHomeBinding
import com.rfk.brewco.ui.cart.CartActivity
import com.rfk.brewco.ui.profile.FormProfileActivity
import com.rfk.brewco.ui.profile.ProfileActivity
import com.rfk.brewco.utils.EXTRA_RESULT
import com.rfk.brewco.utils.EXTRA_TYPE_FORM
import com.rfk.brewco.utils.RESULT_CODE
import com.rfk.brewco.utils.TYPE_ADD

class HomeFragment : Fragment() {

    private lateinit var mUserPreference: UserPreference
    private lateinit var user: User
    private var isPreferenceEmpty = false

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mUserPreference = UserPreference(requireContext())
        showExistingPreference()

        return binding.root
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == RESULT_CODE) {
            user = result.data?.getParcelableExtra<User>(EXTRA_RESULT) as User
            populateView(user)
            checkForm(user)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = binding.ibProfile
        profile.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        val cart = binding.ibCart
        cart.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        val usernameEdit = binding.ibUsernameEdit
        usernameEdit.setOnClickListener {
            val intent = Intent(requireContext(), FormProfileActivity::class.java)
            intent.putExtra(
                EXTRA_TYPE_FORM,
                TYPE_ADD
            )
            intent.putExtra("USER", user)
            resultLauncher.launch(intent)
        }

        recyclerView = binding.rvCoffee
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.coffee.observe(viewLifecycleOwner, Observer(this::showRecyclerView))
    }

    private fun showExistingPreference() {
        user = mUserPreference.getUser()
        populateView(user)
        checkForm(user)
    }

    private fun checkForm(user: User) {
        when {
            user.name.toString().isNotEmpty() -> {
                binding.ibUsernameEdit.visibility = View.GONE
                isPreferenceEmpty = false
            }
            else -> {
                binding.ibUsernameEdit.visibility = View.VISIBLE
                isPreferenceEmpty = true
            }
        }
    }

    private fun populateView(user: User) {
        binding.tvUsername.text = if (user.name.toString().isEmpty()) "Your Name" else user.name
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