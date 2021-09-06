package com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.domain.interactors.IEpisodesInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsEpisodesFragmentViewModel @Inject constructor(
    private val interactor: IEpisodesInteractor
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