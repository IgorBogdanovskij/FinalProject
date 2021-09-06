package com.example.rickyandmorty_finalproject_anderson.data.remote.api.episodes

import com.example.rickyandmorty_finalproject_anderson.data.remote.models.episod.Episode
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.episod.ResponseApiEpisodes
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.ResponseApiHero
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

///character/?page=19
interface ApiQueryEpisodes {

    @GET("episode")
    fun getAllEpisodes(@Query("page") page: Int): Single<ResponseApiEpisodes>

    @GET("episode/{id}")
    fun getSingleEpisode(@Path("id") number: Int): Single<Episode>

    @GET("episode")
    fun getEpisodesByFilters(
        @Query("episode") episode: String = "",
    ): Single<Episode>

    @GET("episode")
    fun getHeroesBySearch(
        @Query("name") name: String
    ): Single<ResponseApiEpisodes>
}