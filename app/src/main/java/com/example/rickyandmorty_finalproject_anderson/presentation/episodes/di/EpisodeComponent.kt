package com.example.rickyandmorty_finalproject_anderson.presentation.episodes.di

import com.example.rickyandmorty_finalproject_anderson.presentation.episodes.ui.EpisodesFragment
import dagger.Subcomponent

@Subcomponent(modules = [EpisodeModule::class])
interface EpisodeComponent {

    fun inject(episodesFragment: EpisodesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): EpisodeComponent
    }
}