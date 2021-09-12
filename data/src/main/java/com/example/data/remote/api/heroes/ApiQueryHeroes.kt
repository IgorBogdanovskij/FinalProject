package com.example.data.remote.api.heroes

import com.example.domain.models.hero.Hero
import com.example.domain.models.hero.ResponseApiHero
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiQueryHeroes {

    @GET("character")
    fun getAllCharacter(@Query("page") page: Int): Single<ResponseApiHero>

    @GET("character/{id}")
    fun getSingleCharacter(@Path("id") number: Int): Single<Hero>

    @GET("character")
    fun getHeroesBySearch(
        @Query("page") page: Int,
        @Query("name") name: String
    ): Single<ResponseApiHero>

    @GET("character")
    fun getHeroesByFilters(
        @Query("page") page: Int,
        @Query("status") status: String = "",
        @Query("species") species: String = "",
        @Query("gender") gender: String = ""
    ): Single<ResponseApiHero>
}