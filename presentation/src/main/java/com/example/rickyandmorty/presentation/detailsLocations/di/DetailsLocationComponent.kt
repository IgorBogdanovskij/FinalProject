package com.example.rickyandmorty.presentation.detailsLocations.di

import com.example.rickyandmorty.presentation.detailsLocations.ui.DetailsLocationsFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailsLocationModule::class])
interface DetailsLocationComponent {

    fun inject(detailsLocationsFragment: DetailsLocationsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsLocationComponent
    }
}