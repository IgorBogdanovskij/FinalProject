package com.example.rickyandmorty.presentation.locations.di

import com.example.rickyandmorty.presentation.locations.ui.LocationsFragment
import dagger.Subcomponent


@Subcomponent(modules = [LocationModule::class])
interface LocationComponent {

    fun inject(locationsFragment: LocationsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LocationComponent
    }
}