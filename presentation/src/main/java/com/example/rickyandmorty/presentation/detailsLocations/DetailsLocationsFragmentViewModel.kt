package com.example.rickyandmorty.presentation.detailsLocations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.domain.interactors.ILocationsInteractor
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsLocationsFragmentViewModel @Inject constructor(
    private val interactor: ILocationsInteractor
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var listCharacters = mutableListOf<HeroEntity>()

    private var _liveData = MutableLiveData<List<HeroEntity>>()
    val liveData: LiveData<List<HeroEntity>>
        get() = _liveData

    fun getSingleHero(number: Int) {
        disposables.addAll(interactor.getSingleCharacterRemote(number = number)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                listCharacters.add(it)
                _liveData.value = listCharacters

            }, {
                Log.d("lo", "getSingleEpisodeError: ${it.message}")
            }))
    }

    override fun onCleared() {
        super.onCleared()

        listCharacters.clear()
        disposables.clear()
    }
}