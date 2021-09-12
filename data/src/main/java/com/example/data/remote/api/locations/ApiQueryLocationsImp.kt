package com.example.data.remote.api.locations

import com.example.domain.models.location.ResponseApiLocations
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  ApiQueryLocationsImp(
    gsonConverterFactory: GsonConverterFactory,
    rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
) : ApiQueryLocations {

    private val mApiQueryLocations: ApiQueryLocations = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava3CallAdapterFactory)
        .build()
        .create(ApiQueryLocations::class.java)

    override fun getAllLocations(page: Int): Single<ResponseApiLocations> {
        return mApiQueryLocations.getAllLocations(page)
    }

    override fun getSingleLocation(number: Int): Single<com.example.domain.models.location.Location> {
        return mApiQueryLocations.getSingleLocation(number = number)
    }

    override fun getLocationsByFilters(
        page: Int,
        type: String,
        dimension: String
    ): Single<ResponseApiLocations> {
        return mApiQueryLocations.getLocationsByFilters(page, type, dimension)
    }

    override fun getHeroesBySearch(name: String): Single<ResponseApiLocations> {
        return mApiQueryLocations.getHeroesBySearch(name)
    }
}


