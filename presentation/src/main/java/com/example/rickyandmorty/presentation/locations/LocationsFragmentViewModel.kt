package com.example.rickyandmorty.presentation.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.domain.interactors.ILocationsInteractor
import com.example.rickyandmorty.domain.models.location.LocationEntity
import com.example.rickyandmorty.presentation.utils.LiveEvent
import com.example.rickyandmorty.presentation.utils.MutableLiveEvent
import com.example.rickyandmorty.presentation.utils.send
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class LocationsFragmentViewModel @Inject constructor(
    private val interactor: ILocationsInteractor
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var _liveData = MutableLiveData<List<LocationEntity>>()
    val liveData: LiveData<List<LocationEntity>>
        get() = _liveData

    private var _liveDataFilters = MutableLiveData<List<LocationEntity>>()
    val liveDataFilters: LiveData<List<LocationEntity>>
        get() = _liveDataFilters

    private var _liveDataFiltersType = MutableLiveData<String>()
    val liveDataFiltersType: LiveData<String>
        get() = _liveDataFiltersType

    private var _liveDataFiltersDimension = MutableLiveData<String>()
    val liveDataFiltersDimension: LiveData<String>
        get() = _liveDataFiltersDimension

    private val _isSearchResultEmptyEvent = MutableLiveEvent<Boolean>()
    val isSearchResultEmptyEvent: LiveEvent<Boolean>
        get() = _isSearchResultEmptyEvent

    private val _isSearchResultEmptyFilters = MutableLiveEvent<Boolean>()
    val isSearchResultEmptyFilters: LiveEvent<Boolean>
        get() = _isSearchResultEmptyFilters

    private val _isLoadingEvent = MutableLiveEvent<Boolean>()
    val isLoadingEvent: LiveEvent<Boolean>
        get() = _isLoadingEvent



    init {
        getLocationsByPage(1)
    }

    fun getLocationsByPage(page: Int) {
        disposables.addAll(interactor.getAllLocationsByPage(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoadingEvent.send(true)
            }
            .doOnSuccess {
                Log.d("lol", "getLocationsByPage: $it")
                _isLoadingEvent.send(false)
            }
            .subscribe(
                {
                    Log.d("lo", "getHeroesByPage: $it")
                    _liveData.value = it
                }, {
                    Log.d("lo", "getHeroesByPageLocations: ${it.message}")
                })
        )
    }

    fun getEpisodeBySearch(name: String) {
        disposables.addAll(interactor.getLocationBySearch(name)
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
                Log.d("lo", "getEpisodeBySearch: ")
            })
        )
    }

    fun setFilters(
        page: Int,
        type: String,
        dimension: String,
    ) {

        _liveDataFiltersType.value = type

        _liveDataFiltersDimension.value = dimension

        disposables.addAll(interactor.getLocationsByFilters(page, type, dimension)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                if (it.isEmpty()) {
                    _isSearchResultEmptyFilters.send(true)
                } else {
                    _isSearchResultEmptyFilters.send(false)
                }
            }
            .subscribe({
                _liveDataFilters.value = it
            }, {
                Log.d("lo", "setFilters: ${it.message}")
            })
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}