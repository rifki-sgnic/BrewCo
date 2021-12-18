package com.rfk.brewco.data.user

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var address: String? = null
) : Parcelable