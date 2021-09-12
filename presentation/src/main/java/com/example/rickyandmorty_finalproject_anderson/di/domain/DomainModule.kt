package com.example.rickyandmorty_finalproject_anderson.di.domain

import com.example.domain.interactors.*
import com.example.domain.repository.IRepositoryEpisodes
import com.example.domain.repository.IRepositoryHeroes
import com.example.domain.repository.IRepositoryLocations
import com.example.rickyandmorty_finalproject_anderson.di.data.DataModule
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    fun provideEpisodeInteractor(
        repo: IRepositoryEpisodes
    ): IEpisodesInteractor {
        return EpisodesInteractor(repo)
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