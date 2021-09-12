package com.example.domain.models.hero

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(

    @ColumnInfo (name = "name_location")
    @SerializedName("name")
    val nameLocation: String,

    @ColumnInfo (name = "url_location")
    @SerializedName("url")
    val urlLocation: String

):Parcelable