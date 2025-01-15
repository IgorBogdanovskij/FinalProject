package com.example.rickyandmorty.data.repository

import com.example.rickyandmorty.data.locale.dao.hero.HeroDao
import com.example.rickyandmorty.data.locale.dao.location.LocationDao
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.domain.models.location.LocationEntity
import com.example.rickyandmorty.data.remote.api.heroes.ApiQueryHeroes
import com.example.rickyandmorty.data.remote.api.locations.ApiQueryLocations
import com.example.rickyandmorty.domain.models.hero.toHeroEntity
import com.example.rickyandmorty.domain.models.location.responseApiEpisodesToLocationEntity
import com.example.rickyandmorty.domain.repository.IRepositoryLocations
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryLocationsImp(
    private val apiQueryHeroes: ApiQueryHeroes,
    private val apiQueryLocations: ApiQueryLocations,

    private val heroDao: HeroDao,
    private val locationDao: LocationDao
) : IRepositoryLocations {

    override fun getLocationBySearch(name: String): Single<List<LocationEntity>> {
        return apiQueryLocations.getHeroesBySearch(name)
            .subscribeOn(Schedulers.io())
            .map { it.responseApiEpisodesToLocationEntity() }
            .onErrorResumeNext { locationDao.getLocationsBySearch(name = name) }
    }

    override fun getAllLocationsRemote(page: Int): Single<List<LocationEntity>> {
        return apiQueryLocations.getAllLocations(page = page)
            .subscribeOn(Schedulers.io())
            .flatMap {
                locationDao.insertLocations(it.responseApiEpisodesToLocationEntity())
                    .andThen(Single.just(it.responseApiEpisodesToLocationEntity()))
            }
            .onErrorResumeNext { locationDao.getAllLocations() }
    }

    override fun getSingleCharacterRemote(number: Int): Single<HeroEntity> {
        return apiQueryHeroes.getSingleCharacter(number = number)
            .subscribeOn(Schedulers.io())
            .flatMap {
                heroDao.insertOneHero(it.toHeroEntity())
                    .andThen(Single.just(it.toHeroEntity()))
            }
            .onErrorResumeNext { heroDao.getSingleHero(id = number) }
    }

    override fun getLocationsByFiltersRemote(
        page: Int,
        type: String,
        dimension: String
    ): Single<List<LocationEntity>> {
        return apiQueryLocations.getLocationsByFilters(page, type, dimension)
            .subscribeOn(Schedulers.io())
            .flatMap {
                locationDao.insertLocations(it.responseApiEpisodesToLocationEntity())
                    .andThen(Single.just(it.responseApiEpisodesToLocationEntity()))
            }
            .onErrorResumeNext { locationDao.getAllLocations() }
            .map {
                it.filter { item ->
                    if (type != "") {
                        item.type.lowercase() == type.lowercase()
                    } else {
                        true
                    }
                }
            }
            .map {
                it.filter { item ->
                    if (dimension != "") {
                        item.dimension.lowercase() == dimension.lowercase()
                    } else {
                        true
                    }
                }
            }

    }
}