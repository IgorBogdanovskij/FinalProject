package com.example.rickyandmorty_finalproject_anderson.presentation.heroes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.domain.interactors.IHeroesInteractor
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.LiveEvent
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.MutableLiveEvent
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.send
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class HeroesFragmentViewModel @Inject constructor(
    private val interactor: IHeroesInteractor
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var _liveData = MutableLiveData<List<HeroEntity>>()
    val liveData: LiveData<List<HeroEntity>>
        get() = _liveData

    private var _liveDataFiltersStatus = MutableLiveData<String>()
    val liveDataFiltersStatus: LiveData<String>
        get() = _liveDataFiltersStatus

    private var _liveDataFiltersSpecies = MutableLiveData<String>()
    val liveDataFiltersSpecies: LiveData<String>
        get() = _liveDataFiltersSpecies

    private var _liveDataFiltersGender = MutableLiveData<String>()
    val liveDataFiltersGender: LiveData<String>
        get() = _liveDataFiltersGender

    private val _isSearchResultEmptyEvent = MutableLiveEvent<Boolean>()
    val isSearchResultEmptyEvent: LiveEvent<Boolean>
        get() = _isSearchResultEmptyEvent

    private val _isSearchResultEmptyEventFilters = MutableLiveEvent<Boolean>()
    val isSearchResultEmptyEventFilters: LiveEvent<Boolean>
        get() = _isSearchResultEmptyEventFilters

    private val _isLoadingEvent = MutableLiveEvent<Boolean>()
    val isLoadingEvent: LiveEvent<Boolean>
        get() = _isLoadingEvent

    init {
        getHeroesByPage(1)
    }

    fun getHeroesByPage(page: Int) {
        disposables.addAll(interactor.getHeroes(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoadingEvent.send(true)
            }
            .doOnSuccess {
                _isLoadingEvent.send(false)
            }
            .subscribe(_liveData::setValue)
        )
    }

    fun getHeroesBySearch(page: Int, name: String) {
        disposables.addAll(
            interactor.getHeroesBySearch(page = page, name = name)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (it.isEmpty()) {
                        _isSearchResultEmptyEvent.send(true)
                    } else {
                        _isSearchResultEmptyEvent.send(false)
                    }
                }
                .subscribe({
                    _liveData.value = it
                }, {
                    Log.d("lo", "getHeroesBySearchError: ${it.message}")
                })
        )
    }

    fun setFilters(
        page: Int,
        status: String,
        species: String,
        gender: String
    ) {

        _liveDataFiltersSpecies.value = species
        _liveDataFiltersStatus.value = status
        _liveDataFiltersGender.value = gender

        disposables.addAll(
            interactor.getHeroesByFilters(page, status, species, gender)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    Log.d("lol", "setFilters: $it")
                    if (it.isEmpty()){
                        _isSearchResultEmptyEventFilters.send(true)
                    }else{
                        _isSearchResultEmptyEventFilters.send(false)
                    }
                }
                .subscribe(_liveData::setValue)
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}