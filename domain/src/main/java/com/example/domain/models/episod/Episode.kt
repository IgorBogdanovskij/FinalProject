package com.example.domain.models.episod

data class Episode(
    val id: Int,
    val air_date: String = "0",
    val characters: List<String>,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)

fun Episode.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(this.id,
        this.air_date,
        this.characters,
        this.created,
        this.episode,
        this.name,
        this.url )
}