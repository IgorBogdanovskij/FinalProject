package com.example.rickyandmorty_finalproject_anderson.domain.repository

import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.location.ResponseApiLocations
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