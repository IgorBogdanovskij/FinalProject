package com.example.rickyandmorty_finalproject_anderson.domain.interactors

import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.domain.repository.IRepositoryEpisodes
import io.reactivex.rxjava3.core.Single


interface IEpisodesInteractor {

    fun getEpisodesByPage(page: Int): Single<List<EpisodeEntity>>

    fun getEpisodesByFilters(episode: String): Single<EpisodeEntity>

    fun getEpisodeBySearch(episode: String): Single<List<EpisodeEntity>>

    fun getSingleCharacterRemote(number: Int): Single<HeroEntity>

}

class EpisodesInteractor(val repo: IRepositoryEpisodes) : IEpisodesInteractor {

    override fun getEpisodesByPage(page: Int): Single<List<EpisodeEntity>> {
        return repo.getAllEpisodesRemote(page)
    }

    override fun getEpisodesByFilters(episode: String): Single<EpisodeEntity> {
        return repo.getEpisodesByFiltersRemote(episode)
    }

    override fun getEpisodeBySearch(episode: String): Single<List<EpisodeEntity>> {
        return repo.getEpisodeBySearch(name = episode)
    }

    override fun getSingleCharacterRemote(number: Int): Single<HeroEntity> {
        return repo.getSingleCharacterRemote(number)
    }
}