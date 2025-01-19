package com.example.rickyandmorty.domain.models.episod

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = EpisodeEntity.TABLE_NAME)
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
) {
    companion object {
        const val TABLE_NAME = "episodes_table"
    }
}

