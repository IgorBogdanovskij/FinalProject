package com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero

import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity

data class ResponseApiHero(
    val info: Info,
    val results: List<Hero>
)

fun ResponseApiHero.responseApiHeroToListHeroEntity(): List<HeroEntity> {

    val listHeroes = mutableListOf<HeroEntity>()

    this.results.forEach {
        listHeroes.add(
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

    return listHeroes
}