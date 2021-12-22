package com.rfk.brewco.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rfk.brewco.R
import com.rfk.brewco.UserPreference
import com.rfk.brewco.data.user.User
import com.rfk.brewco.databinding.ActivityFormProfileBinding
import com.rfk.brewco.utils.*

class FormProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var user: User

    private var _binding: ActivityFormProfileBinding? = null

    private val binding get() = _binding as ActivityFormProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBack = binding.ibNavBack
        navBack.setOnClickListener {
            finish()
        }

        user = intent.getParcelableExtra<User>("USER") as User
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        var btnTitle = ""
        when (formType) {
            TYPE_ADD -> {
                btnTitle = "Save"
            }
            TYPE_EDIT -> {
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }
        binding.btnSave.text = btnTitle
        binding.btnSave.setOnClickListener(this)
    }

    private fun showPreferenceInForm() {
        binding.edtName.setText(user.name)
        binding.edtEmail.setText(user.email)
        binding.edtAddress.setText(user.address.toString())
        binding.edtPhone.setText(user.phoneNumber)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()

            if (name.isEmpty()) {
                binding.edtName.error = FIELD_REQUIRED
                return
            }

            if (email.isEmpty()) {
                binding.edtEmail.error = FIELD_REQUIRED
                return
            }

            if (!isValidEmail(email)) {
                binding.edtEmail.error = FIELD_IS_NOT_VALID
                return
            }

            if (address.isEmpty()) {
                binding.edtAddress.error = FIELD_REQUIRED
                return
            }

            if (phoneNo.isEmpty()) {
                binding.edtPhone.error = FIELD_REQUIRED
                return
            }

            if (!TextUtils.isDigitsOnly(phoneNo)) {
                binding.edtPhone.error = FIELD_DIGIT_ONLY
                return
            }

            saveUser(name, email, address, phoneNo)

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, user)
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUser(name: String, email: String, address: String, phoneNo: String) {
        val userPreference = UserPreference(this)
        user.name = name
        user.email = email
        user.address = address
        user.phoneNumber = phoneNo
        userPreference.setUser(user)
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FIELD_REQUIRED = "Field cannot be empty"
        private const val FIELD_DIGIT_ONLY = "Numeric only"
        private const val FIELD_IS_NOT_VALID = "Email is not valid"
    }
}