package com.example.rickyandmorty

import android.app.Application
import com.example.rickyandmorty.di.DaggerAppComponent
import com.example.rickyandmorty.presentation.utils.unsafeLazy

class App : Application() {
    val appComponent by unsafeLazy {
        DaggerAppComponent.factory().create(this)
    }
}