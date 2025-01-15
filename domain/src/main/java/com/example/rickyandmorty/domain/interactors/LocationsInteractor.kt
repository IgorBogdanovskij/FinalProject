package com.example.rickyandmorty.domain.interactors

import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.domain.models.location.LocationEntity
import com.example.rickyandmorty.domain.repository.IRepositoryLocations
import io.reactivex.rxjava3.core.Single

interface ILocationsInteractor{

    fun getLocationsByFilters(
        page: Int,
        type: String,
        dimension: String
    ): Single<List<LocationEntity>>

    fun getAllLocationsByPage(
        page: Int
    ): Single<List<LocationEntity>>

    fun getSingleCharacterRemote(number: Int): Single<HeroEntity>

    fun getLocationBySearch(name: String): Single<List<LocationEntity>>

}

class LocationsInteractor(val repo: IRepositoryLocations): ILocationsInteractor {
    override fun getLocationsByFilters(
        page: Int,
        type: String,
        dimension: String
    ): Single<List<LocationEntity>> {
        return repo.getLocationsByFiltersRemote(page, type, dimension)
    }

    override fun getAllLocationsByPage(page: Int): Single<List<LocationEntity>> {
        return repo.getAllLocationsRemote(page)
    }

    override fun getSingleCharacterRemote(number: Int): Single<HeroEntity> {
        return repo.getSingleCharacterRemote(number)
    }

    override fun getLocationBySearch(name: String): Single<List<LocationEntity>> {
        return repo.getLocationBySearch(name)
    }
}