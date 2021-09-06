package com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.domain.interactors.IHeroesInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsHeroesFragmentViewModel @Inject constructor(
    private val interactor: IHeroesInteractor
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var _locationEntity = MutableLiveData<LocationEntity>()
    val locationEntity: LiveData<LocationEntity>
        get() = _locationEntity

    private var _locationEntityOrigin = MutableLiveData<LocationEntity>()
    val locationEntityOrigin: LiveData<LocationEntity>
        get() = _locationEntityOrigin

    private var listEpisodes = mutableListOf<EpisodeEntity>()

    private var _liveData = MutableLiveData<List<EpisodeEntity>>()
    val liveData: LiveData<List<EpisodeEntity>>
        get() = _liveData

    fun getSingleEpisode(number: Int) {
        disposables.addAll(interactor.getSingleEpisodeRemote(number = number)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listEpisodes.add(it)

                _liveData.value = listEpisodes
            }, {
                Log.d("lo", "getSingleEpisodeError: ${it.message}")
            }))
    }

    fun getLocationById(id: Int) {
        disposables.addAll(interactor.getSingleLocationRemote(number = id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("lo", "getLocationById: $id")
                _locationEntity.value = it
            }, {
                Log.d("lo", "getLocationById: ${it.message}")
            }))
    }

    fun getOriginLocationById(id: Int) {
        disposables.addAll(interactor.getSingleLocationRemote(number = id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("lo", "getLocationByIdOrigin: $id")
                _locationEntityOrigin.value = it
            }, {
                Log.d("lo", "getLocationById: ${it.message}")
            }))
    }

    override fun onCleared() {
        super.onCleared()

        listEpisodes.clear()
        disposables.clear()
    }
}