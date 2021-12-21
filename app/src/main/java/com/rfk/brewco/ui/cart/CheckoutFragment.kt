package com.rfk.brewco.ui.cart

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rfk.brewco.R
import java.lang.StringBuilder

class CheckoutFragment : RoundedBottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
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

        val payNow = view.findViewById<Button>(R.id.btn_pay_now)

        payNow.setOnClickListener {
            isLoading(true)
            val delay: Long = 3000
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(context, OrderSuccessActivity::class.java)
                startActivity(intent)
            }, delay)
        }
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