package com.example.rickyandmorty.data.remote.api.episodes

import com.example.rickyandmorty.domain.models.episod.Episode
import com.example.rickyandmorty.domain.models.episod.ResponseApiEpisodes
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiQueryEpisodesImp(
    gsonConverterFactory: GsonConverterFactory,
    rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
) : ApiQueryEpisodes {

    private val mApiQueryEpisodes: ApiQueryEpisodes = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava3CallAdapterFactory)
        .build()
        .create(ApiQueryEpisodes::class.java)

    override fun getAllEpisodes(page:Int): Single<ResponseApiEpisodes> {
        return mApiQueryEpisodes.getAllEpisodes(page)
    }

    override fun getSingleEpisode(number: Int): Single<Episode> {
        return mApiQueryEpisodes.getSingleEpisode(number = number)
    }

    override fun getEpisodesByFilters(episode: String): Single<Episode> {
        return mApiQueryEpisodes.getEpisodesByFilters(episode)
    }

    override fun getHeroesBySearch(name: String): Single<ResponseApiEpisodes> {
        return mApiQueryEpisodes.getHeroesBySearch(name = name)
    }
}