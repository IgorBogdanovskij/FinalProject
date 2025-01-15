package com.example.rickyandmorty.presentation.heroes.di

import com.example.rickyandmorty.presentation.heroes.ui.HeroesFragment
import dagger.Subcomponent

@Subcomponent(modules = [HeroModule::class])
interface HeroComponent {

    fun inject(heroesFragment: HeroesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HeroComponent
    }
}




