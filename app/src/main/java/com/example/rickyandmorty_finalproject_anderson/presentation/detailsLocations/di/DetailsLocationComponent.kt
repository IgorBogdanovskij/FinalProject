package com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.di

import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui.DetailsLocationsFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailsLocationModule::class])
interface DetailsLocationComponent {

    fun inject(detailsLocationsFragment: DetailsLocationsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsLocationComponent
    }
}