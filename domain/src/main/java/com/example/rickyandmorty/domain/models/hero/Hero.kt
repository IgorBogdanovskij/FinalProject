package com.example.rickyandmorty.domain.models.hero

data class Hero(

    val id: Int,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val location: Location,
    val origin: Origin,
    val type: String,
    val url: String,
    val created: String,
    val episode: List<String>,
)

fun Hero.toHeroEntity(): HeroEntity {

    return HeroEntity(
        this.id,
        this.gender,
        this.image,
        this.name,
        this.species,
        this.status,
        this.location,
        this.origin,
        this.type,
        this.url,
        this.created,
        this.episode
    )
}

fun Hero.toHeroEntityList(list: List<Hero>): List<HeroEntity> {

    val listHeroEntity = mutableListOf<HeroEntity>()

    list.forEach {
        listHeroEntity.add(
            HeroEntity(
                it.id,
                it.gender,
                it.image,
                it.name,
                it.species,
                it.status,
                it.location,
                it.origin,
                it.type,
                it.url,
                it.created,
                it.episode
            )
        )
    }
    return listHeroEntity
}