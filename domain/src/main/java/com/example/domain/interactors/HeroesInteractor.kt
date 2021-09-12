package com.example.domain.interactors

import com.example.domain.models.episod.EpisodeEntity
import com.example.domain.models.hero.HeroEntity
import com.example.domain.models.location.LocationEntity
import com.example.domain.repository.IRepositoryHeroes
import io.reactivex.rxjava3.core.Single

interface IHeroesInteractor {

    fun getHeroesByFilters(
        page: Int,
        status: String,
        species: String,
        gender: String
    ): Single<List<HeroEntity>>

    fun getHeroesBySearch(
        page: Int,
        name: String
    ): Single<List<HeroEntity>>

    fun getHeroes(
        page: Int
    ): Single<List<HeroEntity>>

    fun getSingleLocationRemote(number: Int): Single<LocationEntity>

    fun getSingleEpisodeRemote(number: Int): Single<EpisodeEntity>

    fun getHeroesList():List<HeroEntity>

    fun getHeroesListById(arr:List<Int>):List<HeroEntity>

}

class HeroesInteractor(val repo: IRepositoryHeroes): IHeroesInteractor {
    override fun getHeroesByFilters(
        page: Int,
        status: String,
        species: String,
        gender: String
    ): Single<List<HeroEntity>> {
        return repo.getHeroesByFiltersRemote(page, status, species, gender)
    }

    override fun getHeroesBySearch(page: Int, name: String): Single<List<HeroEntity>> {
        return repo.getHeroesBySearchRemote(page, name)
    }

    override fun getHeroes(page: Int): Single<List<HeroEntity>> {
        return repo.getHeroesByPageRemote(page)
    }

    override fun getSingleLocationRemote(number: Int): Single<LocationEntity> {
        return repo.getSingleLocationRemote(number)
    }

    override fun getSingleEpisodeRemote(number: Int): Single<EpisodeEntity> {
        return repo.getSingleEpisodeRemote(number)
    }

    override fun getHeroesList(): List<HeroEntity> {
        return repo.getHeroesList()
    }

    override fun getHeroesListById(arr: List<Int>): List<HeroEntity> {
        return repo.getHeroesListById(arr)
    }
}