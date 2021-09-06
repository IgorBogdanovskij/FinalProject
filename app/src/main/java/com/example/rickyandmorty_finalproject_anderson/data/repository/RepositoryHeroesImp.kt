package com.example.rickyandmorty_finalproject_anderson.data.repository

import android.util.Log
import com.example.rickyandmorty_finalproject_anderson.data.locale.dao.episod.EpisodeDao
import com.example.rickyandmorty_finalproject_anderson.data.locale.dao.hero.HeroDao
import com.example.rickyandmorty_finalproject_anderson.data.locale.dao.location.LocationDao
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.data.remote.api.episodes.ApiQueryEpisodes
import com.example.rickyandmorty_finalproject_anderson.data.remote.api.heroes.ApiQueryHeroes
import com.example.rickyandmorty_finalproject_anderson.data.remote.api.locations.ApiQueryLocations
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.episod.toEpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.responseApiHeroToListHeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.location.toLocationEntity
import com.example.rickyandmorty_finalproject_anderson.domain.repository.IRepositoryHeroes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryHeroesImp(

    private val apiQueryHeroes: ApiQueryHeroes,
    private val apiQueryEpisodes: ApiQueryEpisodes,
    private val apiQueryLocations: ApiQueryLocations,

    private val heroDao: HeroDao,
    private val episodeDao: EpisodeDao,
    private val locationDao: LocationDao

) : IRepositoryHeroes {


    override fun getHeroesListById(arr: List<Int>): List<HeroEntity> {
        return heroDao.getAllHeroesByFilters(arr)
    }

    override fun getHeroesByPageRemote(page: Int): Single<List<HeroEntity>> {
        return apiQueryHeroes.getAllCharacter(page = page)
            .subscribeOn(Schedulers.io())
            .flatMap {
                heroDao.insertHeroes(it.responseApiHeroToListHeroEntity())
                    .andThen(Single.just(it.responseApiHeroToListHeroEntity()))
            }
            .onErrorResumeNext {
                heroDao.getAllHeroes()
            }
    }

    override fun getSingleEpisodeRemote(number: Int): Single<EpisodeEntity> {
        return apiQueryEpisodes.getSingleEpisode(number = number)
            .subscribeOn(Schedulers.io())
            .flatMap {
                episodeDao.insertOneEpisode(it.toEpisodeEntity())
                    .andThen(Single.just(it.toEpisodeEntity()))
            }
            .onErrorResumeNext { episodeDao.getSingleEpisode(number) }
    }

    override fun getSingleLocationRemote(number: Int): Single<LocationEntity> {
        return apiQueryLocations.getSingleLocation(number = number)
            .subscribeOn(Schedulers.io())
            .map {
                it.toLocationEntity()
            }
    }

    override fun getHeroesBySearchRemote(page: Int, name: String): Single<List<HeroEntity>> {
        return apiQueryHeroes.getHeroesBySearch(page = page, name = name)
            .subscribeOn(Schedulers.io())
            .map {
                it.responseApiHeroToListHeroEntity()
            }
            .onErrorResumeNext { heroDao.getHeroesBySearch(name) }
    }

    override fun getHeroesByFiltersRemote(
        page: Int,
        status: String,
        species: String,
        gender: String
    ): Single<List<HeroEntity>> {
        return apiQueryHeroes.getHeroesByFilters(page, status, species, gender)
            .subscribeOn(Schedulers.io())
            .map { it.responseApiHeroToListHeroEntity() }
            .onErrorResumeNext { heroDao.getAllHeroes() }
            .map {
                it.filter { item ->
                    if (status != "") {
                        item.status.lowercase() == status.lowercase()
                    } else {
                        true
                    }
                }
            }
            .map {
                it.filter { item ->
                    if (gender != "") {
                        item.gender.lowercase() == gender.lowercase()
                    } else {
                        true
                    }
                }
            }
            .map {
                it.filter { item ->
                    if (species != "") {
                        item.species.lowercase() == species.lowercase()
                    } else {
                        true
                    }
                }
            }


    }

    override fun getHeroesList(): List<HeroEntity> {
        return heroDao.getAllHeroesWithoutLiveData()
    }
}