package com.example.rickyandmorty_finalproject_anderson.data.remote.models.location

import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity

data class Location(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

fun Location.toLocationEntity(): LocationEntity {
    return LocationEntity(
        this.id,
        this.name,
        this.type,
        this.dimension,
        this.created,
        this.residents,
        this.url
    )
}