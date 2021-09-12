package com.example.rickyandmorty_finalproject_anderson

import android.app.Application
import com.example.rickyandmorty_finalproject_anderson.di.DaggerAppComponent
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.unsafeLazy

class App : Application() {
    val appComponent by unsafeLazy {
        DaggerAppComponent.factory().create(this)
    }
}