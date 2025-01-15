package com.example.rickyandmorty.domain.repository

import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.domain.models.location.LocationEntity
import io.reactivex.rxjava3.core.Single

interface IRepositoryHeroes {

    fun getHeroesListById(arr:List<Int>):List<HeroEntity>
    fun getHeroesByPageRemote(page: Int): Single<List<HeroEntity>>
    fun getSingleEpisodeRemote(number: Int): Single<EpisodeEntity>
    fun getSingleLocationRemote(number: Int): Single<LocationEntity>
    fun getHeroesBySearchRemote(page: Int, name: String): Single<List<HeroEntity>>
    fun getHeroesByFiltersRemote(
        page: Int,
        status: String,
        species: String,
        gender: String
    ): Single<List<HeroEntity>>

    fun getHeroesList(): List<HeroEntity>
}