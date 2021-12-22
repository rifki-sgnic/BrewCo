package com.rfk.brewco

import android.content.Context
import com.rfk.brewco.data.user.User
import com.rfk.brewco.utils.*

internal class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putString(EMAIL, value.email)
        editor.putString(ADDRESS, value.address)
        editor.apply()
    }

    fun getUser(): User {
        val model = User()
        model.name = preferences.getString(NAME, "")
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        model.email = preferences.getString(EMAIL, "")
        model.address = preferences.getString(ADDRESS, "")
        return model
    }
}