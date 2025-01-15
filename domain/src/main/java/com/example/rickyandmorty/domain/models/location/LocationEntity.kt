package com.example.rickyandmorty.domain.models.location

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = LocationEntity.TABLE_NAME)
@Parcelize
data class LocationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val created: String,
    val residents: List<String>,
    val url: String
):Parcelable {
    companion object {
        const val TABLE_NAME = "location_table"
    }
}

