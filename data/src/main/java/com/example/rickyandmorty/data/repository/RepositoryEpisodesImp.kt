package com.example.rickyandmorty.data.repository

import com.example.rickyandmorty.data.locale.dao.episod.EpisodeDao
import com.example.rickyandmorty.data.remote.api.episodes.ApiQueryEpisodes
import com.example.rickyandmorty.data.remote.api.heroes.ApiQueryHeroes
import com.example.rickyandmorty.data.locale.dao.hero.HeroDao
import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.domain.models.episod.responseApiEpisodesToEpisodeEntity
import com.example.rickyandmorty.domain.models.episod.toEpisodeEntity
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.domain.models.hero.toHeroEntity
import com.example.rickyandmorty.domain.repository.IRepositoryEpisodes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryEpisodesImp(
    private val heroDao: HeroDao,
    private val episodeDao: EpisodeDao,
    private val apiQueryHeroes: ApiQueryHeroes,
    private val apiQueryEpisodes: ApiQueryEpisodes,
) : IRepositoryEpisodes {

    override fun getEpisodeBySearch(name: String): Single<List<EpisodeEntity>> {
        return apiQueryEpisodes.getHeroesBySearch(name = name)
            .subscribeOn(Schedulers.io())
            .map {
                it.responseApiEpisodesToEpisodeEntity()
            }
            .onErrorResumeNext { episodeDao.getEpisodesBySearch(name = name) }

    }

    override fun getAllEpisodesRemote(page: Int): Single<List<EpisodeEntity>> {
        return apiQueryEpisodes.getAllEpisodes(page = page)
            .subscribeOn(Schedulers.io())
            .flatMap {
                episodeDao.insertEpisodes(it.responseApiEpisodesToEpisodeEntity())
                    .andThen(Single.just(it.responseApiEpisodesToEpisodeEntity()))
            }
            .onErrorResumeNext { episodeDao.getAllEpisodes() }
    }

    override fun getSingleCharacterRemote(number: Int): Single<HeroEntity> {
        return apiQueryHeroes.getSingleCharacter(number = number)
            .subscribeOn(Schedulers.io())
            .flatMap {
                heroDao.insertOneHero(it.toHeroEntity())
                    .andThen(Single.just(it.toHeroEntity()))
            }
            .onErrorResumeNext { heroDao.getSingleHero(id = number) }
    }

    override fun getEpisodesByFiltersRemote(episode: String): Single<EpisodeEntity> {
        return apiQueryEpisodes.getEpisodesByFilters(episode)
            .subscribeOn(Schedulers.io())
            .flatMap {
                episodeDao.insertOneEpisode(it.toEpisodeEntity())
                    .andThen(Single.just(it.toEpisodeEntity()))
            }
            .onErrorResumeNext { episodeDao.getSingleEpisode(episode) }
    }
}