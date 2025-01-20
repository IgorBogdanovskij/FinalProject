package com.example.rickyandmorty.presentation.episodes.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.presentation.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty.presentation.di.viewModel.ViewModelKey
import com.example.rickyandmorty.presentation.episodes.EpisodesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class EpisodeModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesFragmentViewModel::class)
    abstract fun bindWeatherViewModel(viewModel: EpisodesFragmentViewModel): ViewModel
}