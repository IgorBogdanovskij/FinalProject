package com.example.rickyandmorty.data.remote.api.heroes


import com.example.rickyandmorty.domain.models.hero.Hero
import com.example.rickyandmorty.domain.models.hero.ResponseApiHero
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiQueryHeroesImp(
    gsonConverterFactory: GsonConverterFactory,
    rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
) : ApiQueryHeroes {

    private val mApiQueryHeroes: ApiQueryHeroes = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava3CallAdapterFactory)
        .build()
        .create(ApiQueryHeroes::class.java)

    override fun getAllCharacter(page: Int): Single<ResponseApiHero> {
        return mApiQueryHeroes.getAllCharacter(page)
    }

    override fun getSingleCharacter(number: Int): Single<Hero> {
        return mApiQueryHeroes.getSingleCharacter(number = number)
    }

    override fun getHeroesBySearch(page: Int, name: String): Single<ResponseApiHero> {
        return mApiQueryHeroes.getHeroesBySearch(page = page, name = name)
    }

    override fun getHeroesByFilters(
        page: Int,
        status: String,
        species: String,
        gender: String
    ): Single<ResponseApiHero> {
        return mApiQueryHeroes.getHeroesByFilters(page, status, species, gender)
    }
}