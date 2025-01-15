package com.example.rickyandmorty.presentation.episodes.di

import com.example.rickyandmorty.presentation.episodes.ui.EpisodesFragment
import dagger.Subcomponent

@Subcomponent(modules = [EpisodeModule::class])
interface EpisodeComponent {

    fun inject(episodesFragment: EpisodesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): EpisodeComponent
    }
}