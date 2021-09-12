package com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelKey
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.DetailsLocationsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelBuilderModule::class])

abstract class DetailsLocationModule {
        @Binds
        @IntoMap
        @ViewModelKey(DetailsLocationsFragmentViewModel::class)
        abstract fun bindDetailsLocationViewModel(viewModel: DetailsLocationsFragmentViewModel): ViewModel
}