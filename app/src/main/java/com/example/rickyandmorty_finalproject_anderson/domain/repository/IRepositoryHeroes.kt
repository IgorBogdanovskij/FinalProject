package com.example.rickyandmorty_finalproject_anderson.domain.repository

import androidx.lifecycle.LiveData
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.ResponseApiHero
import io.reactivex.rxjava3.core.Completable
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