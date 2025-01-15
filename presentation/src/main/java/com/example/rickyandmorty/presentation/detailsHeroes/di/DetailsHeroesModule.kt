package com.example.rickyandmorty.presentation.detailsHeroes.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty.di.viewModel.ViewModelKey
import com.example.rickyandmorty.presentation.detailsHeroes.DetailsHeroesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class DetailsHeroesModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailsHeroesFragmentViewModel::class)
    abstract fun bindDetailsHeroesViewModel(viewModel: DetailsHeroesFragmentViewModel): ViewModel
}