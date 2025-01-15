package com.example.rickyandmorty.presentation.detailsEpisodes.di

import com.example.rickyandmorty.presentation.detailsEpisodes.ui.DetailsEpisodesFragment
import dagger.Subcomponent


@Subcomponent(modules = [DetailsEpisodesModule::class])
interface DetailsEpisodesComponent {


    fun inject(detailsEpisodesFragment: DetailsEpisodesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsEpisodesComponent
    }
}