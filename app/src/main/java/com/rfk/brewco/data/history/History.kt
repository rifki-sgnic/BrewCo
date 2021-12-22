package com.rfk.brewco.data.history

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class History(
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

    @ColumnInfo(name = "total")
    @NonNull
    var total: Int,

    @ColumnInfo(name = "address")
    @NonNull
    var address: String
) : Parcelable