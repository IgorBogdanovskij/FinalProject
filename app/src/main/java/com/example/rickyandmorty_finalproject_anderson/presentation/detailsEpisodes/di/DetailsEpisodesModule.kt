package com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelKey
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.DetailsEpisodesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class DetailsEpisodesModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsEpisodesFragmentViewModel::class)
    abstract fun bindDetailsEpisodeViewModel(viewModel: DetailsEpisodesFragmentViewModel): ViewModel
}