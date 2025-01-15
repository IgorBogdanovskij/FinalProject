package com.example.rickyandmorty.presentation.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty.domain.interactors.IEpisodesInteractor
import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.presentation.utils.LiveEvent
import com.example.rickyandmorty.presentation.utils.MutableLiveEvent
import com.example.rickyandmorty.presentation.utils.send
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class EpisodesFragmentViewModel @Inject constructor(
    private val episodesInteractor: com.example.rickyandmorty.domain.interactors.IEpisodesInteractor
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var _liveData = MutableLiveData<List<EpisodeEntity>>()
    val liveData: LiveData<List<EpisodeEntity>>
        get() = _liveData

    private var _liveDataFilters = MutableLiveData<EpisodeEntity>()
    val liveDataFilters: LiveData<EpisodeEntity>
        get() = _liveDataFilters

    private var _liveDataSearch = MutableLiveData<List<EpisodeEntity>>()
    val liveDataSearch: LiveData<List<EpisodeEntity>>
        get() = _liveDataSearch

    private var _liveDataFiltersEpisode = MutableLiveData<String>()
    val liveDataFiltersEpisode: LiveData<String>
        get() = _liveDataFiltersEpisode

    private val _isSearchResultEmptyEvent = MutableLiveEvent<Boolean>()
    val isSearchResultEmptyEvent: LiveEvent<Boolean>
        get() = _isSearchResultEmptyEvent

    private val _isLoadingEvent = MutableLiveEvent<Boolean>()
    val isLoadingEvent: LiveEvent<Boolean>
        get() = _isLoadingEvent

    init {
        getNextPage(1)
    }

    fun getNextPage(page: Int) {
        disposables.addAll(episodesInteractor.getEpisodesByPage(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoadingEvent.send(true)
            }
            .doOnSuccess {
                _isLoadingEvent.send(false)
            }
            .subscribe({
                _liveData.value = it
            }, {
                Log.d("lo", "getNextPageEpisodes: ${it.message}")
            }))
    }

    fun getEpisodeBySearch(name: String) {
        disposables.addAll(episodesInteractor.getEpisodeBySearch(episode = name)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                if (it.isEmpty()){
                    _isSearchResultEmptyEvent.send(true)
                }else{
                    _isSearchResultEmptyEvent.send(false)
                }
            }
            .subscribe({
                _liveDataSearch.value = it
            }, {
                Log.d("lo", "getEpisodeBySearch: ${it.message}")
            }))
    }

    fun setFilters(
        episode: String,
    ) {

        _liveDataFiltersEpisode.value = episode

        disposables.addAll(episodesInteractor.getEpisodesByFilters(episode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _liveDataFilters.value = it
            }, {
                Log.d("lo", "setFilters: ${it.message}")
            }))
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}