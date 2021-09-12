package com.example.domain.models.location

import com.example.domain.models.location.LocationEntity

data class ResponseApiLocations(
    val info: Info,
    val results: List<Location>
)

fun ResponseApiLocations.responseApiEpisodesToLocationEntity(): List<LocationEntity> {

    val listLocations = mutableListOf<LocationEntity>()

    this.results.forEach {
        listLocations.add(
            LocationEntity(
            it.id,
            it.name,
            it.type,
            it.dimension,
            it.created,
            it.residents,
            it.url
        )
        )
    }

    return listLocations
}