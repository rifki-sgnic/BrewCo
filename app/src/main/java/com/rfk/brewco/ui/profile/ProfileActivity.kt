package com.rfk.brewco.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rfk.brewco.R
import com.rfk.brewco.UserPreference
import com.rfk.brewco.data.user.User
import com.rfk.brewco.databinding.ActivityProfileBinding
import com.rfk.brewco.utils.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false

    private lateinit var user: User

    private var _binding: ActivityProfileBinding? = null

    private val binding get() = _binding as ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            finish()
        }

        binding.ibEdit.setOnClickListener(this)

        mUserPreference = UserPreference(this)
        showExistingPreference()
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

    private fun showExistingPreference() {
        user = mUserPreference.getUser()
        populateView(user)
        checkForm(user)
    }

    private fun populateView(user: User) {
        binding.tvProfileName.text = if (user.name.toString().isEmpty()) "Your Name" else user.name
        binding.tvProfilePhone.text =
            if (user.phoneNumber.toString().isEmpty()) "Your Number" else user.phoneNumber
        binding.tvProfileEmail.text =
            if (user.email.toString().isEmpty()) "Your Email" else user.email
        binding.tvProfileAddress.text =
            if (user.address.toString().isEmpty()) "Your Address" else user.address
    }

    private fun checkForm(user: User) {
        when {
            user.name.toString().isNotEmpty() -> {
                binding.ibEdit.setImageResource(R.drawable.ic_edit)
                isPreferenceEmpty = false
            }
            else -> {
                binding.ibEdit.setImageResource(R.drawable.ic_baseline_add_24)
                isPreferenceEmpty = true
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.ib_edit) {
            val intent = Intent(this@ProfileActivity, FormProfileActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    intent.putExtra(
                        EXTRA_TYPE_FORM,
                        TYPE_ADD
                    )
                    intent.putExtra("USER", user)
                }
                else -> {
                    intent.putExtra(
                        EXTRA_TYPE_FORM,
                        TYPE_EDIT
                    )
                    intent.putExtra("USER", user)
                }
            }
            resultLauncher.launch(intent)
        }
    }
}