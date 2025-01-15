package com.example.rickyandmorty.di.domain

import com.example.rickyandmorty.domain.interactors.*
import com.example.rickyandmorty.domain.repository.IRepositoryEpisodes
import com.example.rickyandmorty.domain.repository.IRepositoryHeroes
import com.example.rickyandmorty.domain.repository.IRepositoryLocations
import com.example.rickyandmorty.di.data.DataModule
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    fun provideEpisodeInteractor(
        repo: IRepositoryEpisodes
    ): com.example.rickyandmorty.domain.interactors.IEpisodesInteractor {
        return com.example.rickyandmorty.domain.interactors.EpisodesInteractor(repo)
    }

    @Provides
    fun provideHeroInteractor(
        repo: IRepositoryHeroes
    ): IHeroesInteractor {
        return HeroesInteractor(repo)
    }

    @Provides
    fun provideLocationInteractor(
        repo: IRepositoryLocations
    ): ILocationsInteractor {
        return LocationsInteractor(repo)
    }
}