package com.rfk.brewco.ui.cart

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rfk.brewco.R
import com.rfk.brewco.UserPreference
import com.rfk.brewco.ViewModelFactory
import com.rfk.brewco.data.cart.Cart
import com.rfk.brewco.data.history.History
import com.rfk.brewco.data.user.User
import com.rfk.brewco.ui.profile.FormProfileActivity
import com.rfk.brewco.utils.EXTRA_RESULT
import com.rfk.brewco.utils.EXTRA_TYPE_FORM
import com.rfk.brewco.utils.RESULT_CODE
import com.rfk.brewco.utils.TYPE_EDIT

class CheckoutFragment : RoundedBottomSheetDialogFragment() {

    private lateinit var mUserPreference: UserPreference
    private lateinit var user: User
    var arrayList: ArrayList<Cart> = ArrayList()

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == RESULT_CODE) {
            user = result.data?.getParcelableExtra<User>(EXTRA_RESULT) as User
            populateView(user)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = RoundedBottomSheetDialog(requireContext())
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.skipCollapsed = true
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getInt("key")

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        mUserPreference = UserPreference(requireContext())
        showExistingPreference()

        val ibEdit = view.findViewById<ImageButton>(R.id.ib_checkout_edit)
        ibEdit.setOnClickListener {
            val intent = Intent(requireContext(), FormProfileActivity::class.java)
            intent.putExtra(
                EXTRA_TYPE_FORM,
                TYPE_EDIT
            )
            intent.putExtra("USER", user)
            resultLauncher.launch(intent)
        }

        isLoading(false)

        if (data != null) {
            val subtotal = view.findViewById<TextView>(R.id.tv_checkout_subtotal)
            subtotal.text = StringBuilder("Rp ").append(data.toString())

            val feeCharge = if (data < 100000) {
                5000
            } else {
                10000
            }

            val fee = view.findViewById<TextView>(R.id.tv_checkout_delivery_fee)
            fee.text = StringBuilder("Rp ").append(feeCharge.toString())

            val total = view.findViewById<TextView>(R.id.tv_checkout_total_price)
            total.text = StringBuilder("Rp ").append(calculate(data, feeCharge).toString())
        }

        viewModel.cart.observe(this, { cart ->
            if (cart != null) {
                arrayList.addAll(cart.toList())
                Log.d("List", arrayList.toString())
            }
        })

        val payNow = view.findViewById<Button>(R.id.btn_pay_now)

        payNow.setOnClickListener {
            isLoading(true)

            val delay: Long = 3000
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(context, OrderSuccessActivity::class.java)
                addToHistory()
                viewModel.deleteAllCart()
                startActivity(intent)
            }, delay)
        }
    }

    private fun addToHistory() {
        for (cartItem in arrayList) {
            val history = History(
                name = cartItem.name,
                qty = cartItem.qty,
                total = cartItem.total,
                address = user.address.toString()
            )
            viewModel.insertHistory(history)
        }
    }

    private fun showExistingPreference() {
        user = mUserPreference.getUser()
        populateView(user)
    }

    private fun populateView(user: User) {
        val tvName = view?.findViewById<TextView>(R.id.tv_checkout_name)
        val tvAddress = view?.findViewById<TextView>(R.id.tv_checkout_delivery_address)

        tvName?.text = if (user.name.toString().isEmpty()) "Your Name" else user.name
        tvAddress?.text = if (user.address.toString().isEmpty()) "Your Address" else user.address
    }

    private fun calculate(subtotal: Int, fee: Int): Int {
        return subtotal + fee
    }

    private fun isLoading(state: Boolean) {
        val progressBar = view?.findViewById<ProgressBar>(R.id.pb_loading)
        if (state) {
            progressBar?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.GONE
        }
    }
}