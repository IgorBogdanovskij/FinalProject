package com.example.rickyandmorty_finalproject_anderson.presentation.heroes.di

import com.example.rickyandmorty_finalproject_anderson.presentation.episodes.di.HeroModule
import com.example.rickyandmorty_finalproject_anderson.presentation.heroes.ui.HeroesFragment
import dagger.Subcomponent

@Subcomponent(modules = [HeroModule::class])
interface HeroComponent {

    fun inject(heroesFragment: HeroesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HeroComponent
    }
}




