package com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.FragmentDetailsHeroesBinding
import com.example.rickyandmorty_finalproject_anderson.App
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.ui.DetailsEpisodesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.DetailsHeroesFragmentViewModel
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.recycler.AdapterDetailsHeroes
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui.DetailsLocationsFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

class DetailsHeroesFragment : Fragment(R.layout.fragment_details_heroes) {

    private lateinit var location: LocationEntity
    private lateinit var locationOrigin: LocationEntity

    private lateinit var mAdapterDetailsEpisodesHeroes: AdapterDetailsHeroes

    private lateinit var mBinding: FragmentDetailsHeroesBinding

    private lateinit var mHero: HeroEntity

    private lateinit var mOnHideShowBottomNavigationFilterHeroes: OnHideShowBottomNavigationFilterHeroes
    private lateinit var mOnClickBackDetailsHeroes: OnClickBackDetailsHeroes

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailsHeroesFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mHero = arguments?.getParcelable(KEY_BUNDLE_HERO)!!
        mOnHideShowBottomNavigationFilterHeroes = context as OnHideShowBottomNavigationFilterHeroes
        mOnClickBackDetailsHeroes = context as OnClickBackDetailsHeroes
        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .detailsHeroComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsHeroesBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mOnHideShowBottomNavigationFilterHeroes.onHideHeroesDetails()

        mBinding.toolbarDetailsHeroes.navigationIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)

        mBinding.toolbarDetailsHeroes.setOnClickListener { mOnClickBackDetailsHeroes.onClickDetailsHero() }

        mAdapterDetailsEpisodesHeroes =
            AdapterDetailsHeroes(object : AdapterDetailsHeroes.OnClickEventDetailsHeroesRv {
                override fun onClickEpisode(episodeEntity: EpisodeEntity) {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.containerFragments,
                        DetailsEpisodesFragment.newInstance(episodeEntity = episodeEntity)
                    ).addToBackStack(null)
                        .commit()
                }
            }, object : AdapterDetailsHeroes.OnHolderEventListener {
                override fun onHideProgressBar() {
                    mBinding.progressBarRecyclerDetailsHero.visibility = View.GONE
                }

            })

        setupFragment()

        mBinding.detailsEpisodesRecyclerViewHeroes.layoutManager =
            LinearLayoutManager(requireContext())

        mBinding.detailsEpisodesRecyclerViewHeroes.adapter = mAdapterDetailsEpisodesHeroes

        getIdListEpisodes(mHero).forEach {
            viewModel.getSingleEpisode(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            mAdapterDetailsEpisodesHeroes.setData(it)
        }

        viewModel.locationEntityOrigin.observe(viewLifecycleOwner, {
            locationOrigin = it
        })

        viewModel.locationEntity.observe(viewLifecycleOwner, {
            location = it
        })

        setupNavigateLocation()
    }

    private fun setupNavigateLocation() {

        if (getIdLocation(mHero) != "https:") {

            viewModel.getLocationById(getIdLocation(mHero).toInt())

            mBinding.detailsLocationHeroesLinear.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    R.id.containerFragments,
                    DetailsLocationsFragment.newInstance(location)
                ).addToBackStack(null)
                    .commit()
            }
        }

        if (getIdOriginLocation(mHero) != "") {

            viewModel.getOriginLocationById(getIdOriginLocation(mHero).toInt())

            mBinding.detailsOriginHeroesLinear.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    R.id.containerFragments,
                    DetailsLocationsFragment.newInstance(locationOrigin)
                ).addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupFragment() {
        mBinding.detailsNameHeroes.text = mHero.name
        mBinding.detailsStatusHeroes.text = mHero.status

        mBinding.detailsGenderTypeHeroes.text = mHero.gender

        mBinding.detailsOriginNameHeroes.text = mHero.origin.nameOrigin
        mBinding.detailsLocationNameHeroes.text = mHero.location.nameLocation

        mBinding.detailsTypeHeroes.text = mHero.species

        Glide.with(requireContext())
            .load(mHero.image)
            .apply(bitmapTransform(BlurTransformation(22)))
            .into(mBinding.detailsImageHeroes)

        Glide.with(requireContext())
            .load(mHero.image)
            .into(mBinding.detailsImageHeroesCircle)
    }

    private fun getIdListEpisodes(hero: HeroEntity): List<Int> {
        val listId = mutableListOf<Int>()

        hero.episode.forEach {
            val list = it.split("/")
            listId.add(list[list.size - 1].toInt())
        }

        return listId
    }

    private fun getIdOriginLocation(hero: HeroEntity) = hero.origin.urlOrigin.split("/")
        .get(index = hero.origin.urlOrigin.split("/").size - 1)

    private fun getIdLocation(hero: HeroEntity) = hero.location.urlLocation.split("/")
        .get(index = hero.location.urlLocation.split("/").size - 1)

    interface OnClickBackDetailsHeroes {
        fun onClickDetailsHero()
    }

    interface OnHideShowBottomNavigationFilterHeroes {
        fun onHideHeroesDetails()
    }

    companion object {

        const val KEY_BUNDLE_HERO = "KEY_BUNDLE_HERO"

        fun newInstance(hero: HeroEntity): DetailsHeroesFragment {
            return DetailsHeroesFragment().apply {
                val bundle = Bundle().apply {
                    putParcelable(KEY_BUNDLE_HERO, hero)
                }
                arguments = bundle
            }
        }
    }
}