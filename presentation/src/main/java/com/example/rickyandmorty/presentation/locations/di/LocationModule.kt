package com.example.rickyandmorty.presentation.locations.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty.di.viewModel.ViewModelKey
import com.example.rickyandmorty.presentation.locations.LocationsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelBuilderModule::class])
abstract class LocationModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationsFragmentViewModel::class)
    abstract fun bindLocationViewModel(viewModel: LocationsFragmentViewModel): ViewModel
}