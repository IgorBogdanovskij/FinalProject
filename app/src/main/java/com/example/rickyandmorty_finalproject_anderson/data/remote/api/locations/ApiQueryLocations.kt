package com.example.rickyandmorty_finalproject_anderson.data.remote.api.locations

import com.example.rickyandmorty_finalproject_anderson.data.remote.models.episod.Episode
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.episod.ResponseApiEpisodes
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.ResponseApiHero
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.location.Location
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.location.ResponseApiLocations
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

///character/?page=19
interface ApiQueryLocations {

    @GET("location")
    fun getAllLocations(@Query("page") page: Int): Single<ResponseApiLocations>

    @GET("location/{id}")
    fun getSingleLocation(@Path("id") number: Int): Single<Location>

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