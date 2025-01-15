package com.example.rickyandmorty.domain.repository

import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import io.reactivex.rxjava3.core.Single

interface IRepositoryEpisodes {

    fun getEpisodeBySearch(name: String): Single<List<EpisodeEntity>>
    fun getAllEpisodesRemote(page: Int): Single<List<EpisodeEntity>>
    fun getSingleCharacterRemote(number:Int): Single<HeroEntity>
    fun getEpisodesByFiltersRemote(
        episode: String,
    ): Single<EpisodeEntity>
}