package com.rfk.brewco.data.user

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @NonNull
    var name: String? = null,

    @ColumnInfo(name = "phone")
    @NonNull
    var phoneNumber: String? = null,

    @ColumnInfo(name = "email")
    @NonNull
    var email: String? = null,

    @ColumnInfo(name = "address")
    @NonNull
    var address: String? = null
) : Parcelable