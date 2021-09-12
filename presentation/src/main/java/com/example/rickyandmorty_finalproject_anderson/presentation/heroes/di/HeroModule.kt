package com.example.rickyandmorty_finalproject_anderson.presentation.heroes.di

import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelBuilderModule
import com.example.rickyandmorty_finalproject_anderson.di.viewModel.ViewModelKey
import com.example.rickyandmorty_finalproject_anderson.presentation.heroes.HeroesFragmentViewModel
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