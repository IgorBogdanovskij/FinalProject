package com.example.rickyandmorty.presentation.heroes.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty.di.viewModel.ViewModelKey
import com.example.rickyandmorty.presentation.heroes.HeroesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelBuilderModule::class])
abstract class HeroModule {

    @Binds
    @IntoMap
    @ViewModelKey(HeroesFragmentViewModel::class)
    abstract fun bindHeroViewModel(viewModel: HeroesFragmentViewModel): ViewModel
}