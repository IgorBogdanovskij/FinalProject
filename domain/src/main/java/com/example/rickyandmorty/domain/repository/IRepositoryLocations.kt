package com.example.rickyandmorty.domain.repository

import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.domain.models.location.LocationEntity
import io.reactivex.rxjava3.core.Single

interface IRepositoryLocations {

    fun getLocationBySearch(name: String): Single<List<LocationEntity>>
    fun getAllLocationsRemote(page: Int): Single<List<LocationEntity>>
    fun getSingleCharacterRemote(number: Int): Single<HeroEntity>
    fun getLocationsByFiltersRemote(
        page: Int,
        type: String,
        dimension: String
    ): Single<List<LocationEntity>>
}