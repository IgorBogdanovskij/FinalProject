package com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.di

import com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.ui.DetailsEpisodesFragment
import dagger.Subcomponent


@Subcomponent(modules = [DetailsEpisodesModule::class])
interface DetailsEpisodesComponent {


    fun inject(detailsEpisodesFragment: DetailsEpisodesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsEpisodesComponent
    }
}