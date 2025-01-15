package com.example.rickyandmorty.domain.models.location

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