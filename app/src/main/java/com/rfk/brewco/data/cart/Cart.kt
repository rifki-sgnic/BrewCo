package com.rfk.brewco.data.cart

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Cart(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @NonNull
    var name: String,

    @ColumnInfo(name = "qty")
    @NonNull
    var qty: Int,

    @ColumnInfo(name = "shot")
    @NonNull
    var shot: String,

    @ColumnInfo(name = "type")
    @NonNull
    var type: String,

    @ColumnInfo(name = "size")
    @NonNull
    var size: String,

    @ColumnInfo(name = "ice")
    @NonNull
    var ice: String,

    @ColumnInfo(name = "total")
    @NonNull
    var total: Int
) : Parcelable