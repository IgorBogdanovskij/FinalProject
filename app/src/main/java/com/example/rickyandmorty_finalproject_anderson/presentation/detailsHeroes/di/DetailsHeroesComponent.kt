package com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.di

import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.DetailsHeroesFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailsHeroesModule::class])
interface DetailsHeroesComponent {
    fun inject(detailsHeroesFragment: DetailsHeroesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsHeroesComponent
    }
}