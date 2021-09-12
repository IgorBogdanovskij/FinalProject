package com.example.data.remote.api.locations

import com.example.domain.models.location.ResponseApiLocations
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiQueryLocations {

    @GET("location")
    fun getAllLocations(@Query("page") page: Int): Single<ResponseApiLocations>

    @GET("location/{id}")
    fun getSingleLocation(@Path("id") number: Int): Single<com.example.domain.models.location.Location>

    @GET("location")
    fun getLocationsByFilters(
        @Query("page") page: Int,
        @Query("type") type: String = "",
        @Query("dimension") dimension: String = "",
    ): Single<ResponseApiLocations>

    @GET("location")
    fun getHeroesBySearch(
        @Query("name") name: String
    ): Single<ResponseApiLocations>

}