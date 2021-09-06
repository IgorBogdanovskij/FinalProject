package com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Origin(

    @ColumnInfo(name = "name_origin")
    @SerializedName("name")
    val nameOrigin: String,

    @ColumnInfo(name = "url_origin")
    @SerializedName("url")
    val urlOrigin: String
):Parcelable