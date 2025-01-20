package com.example.rickyandmorty.presentation.di

import android.content.Context
import com.example.rickyandmorty.presentation.di.data.DataModule
import com.example.rickyandmorty.presentation.di.data.NetworkModule
import com.example.rickyandmorty.presentation.di.domain.DomainModule
import com.example.rickyandmorty.presentation.detailsEpisodes.di.DetailsEpisodesComponent
import com.example.rickyandmorty.presentation.detailsHeroes.di.DetailsHeroesComponent
import com.example.rickyandmorty.presentation.detailsLocations.di.DetailsLocationComponent
import com.example.rickyandmorty.presentation.episodes.di.EpisodeComponent
import com.example.rickyandmorty.presentation.heroes.di.HeroComponent
import com.example.rickyandmorty.presentation.locations.di.LocationComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NetworkModule::class, DomainModule::class])
interface AppComponent {

    fun episodeComponent(): EpisodeComponent.Factory
    fun locationComponent(): LocationComponent.Factory
    fun heroComponent(): HeroComponent.Factory
    fun detailsEpisodeComponent(): DetailsEpisodesComponent.Factory
    fun detailsHeroComponent(): DetailsHeroesComponent.Factory
    fun detailsLocationComponent(): DetailsLocationComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}