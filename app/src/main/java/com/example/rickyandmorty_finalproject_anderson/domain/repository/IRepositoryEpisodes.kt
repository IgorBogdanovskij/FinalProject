package com.example.rickyandmorty_finalproject_anderson.domain.repository

import androidx.lifecycle.LiveData
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import io.reactivex.rxjava3.core.Single

interface IRepositoryEpisodes {

    fun getEpisodeBySearch(name: String): Single<List<EpisodeEntity>>
    fun getAllEpisodesRemote(page: Int): Single<List<EpisodeEntity>>
    fun getSingleCharacterRemote(number:Int): Single<HeroEntity>
    fun getEpisodesByFiltersRemote(
        episode: String,
    ): Single<EpisodeEntity>
}