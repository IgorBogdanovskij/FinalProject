package com.example.rickyandmorty.domain.models.episod

data class ResponseApiEpisodes(
    val info: Info,
    val results: List<Episode>
)

fun ResponseApiEpisodes.responseApiEpisodesToEpisodeEntity(): List<EpisodeEntity> {

    var listEpisodes = mutableListOf<EpisodeEntity>()

    this.results.forEach {
        listEpisodes.add(
            EpisodeEntity(
                it.id,
                it.air_date,
                it.characters,
                it.created,
                it.episode,
                it.name,
                it.url
            )
        )
    }

    return listEpisodes
}