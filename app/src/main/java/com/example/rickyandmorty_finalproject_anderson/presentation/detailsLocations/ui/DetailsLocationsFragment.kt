package com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.FragmentDetailsLocationsBinding
import com.example.rickyandmorty_finalproject_anderson.App
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.DetailsHeroesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui.recycler.AdapterDetailsLocations
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.DetailsLocationsFragmentViewModel
import javax.inject.Inject


class DetailsLocationsFragment : Fragment(R.layout.fragment_details_locations) {

    private lateinit var mAdapterDetailsLocationsHeroes: AdapterDetailsLocations

    private lateinit var mBinding: FragmentDetailsLocationsBinding
    private lateinit var mLocation: LocationEntity

    private lateinit var mOnHideShowBottomNavigationFilterLocation: OnHideShowBottomNavigationFilterLocation
    private lateinit var mOnClickBackDetailsLocation: OnClickBackDetailsLocation

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailsLocationsFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mOnHideShowBottomNavigationFilterLocation =
            context as OnHideShowBottomNavigationFilterLocation
        mOnClickBackDetailsLocation = context as OnClickBackDetailsLocation
        mLocation = arguments?.getParcelable(KEY_BUNDLE_LOCATION)!!
        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .detailsLocationComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsLocationsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mOnHideShowBottomNavigationFilterLocation.onHideLocationDetails()

        mBinding.toolbarDetailsLocations.navigationIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)

        mBinding.toolbarDetailsLocations.setOnClickListener { mOnClickBackDetailsLocation.onClickDetailsLocation() }

        mAdapterDetailsLocationsHeroes =
            AdapterDetailsLocations(
                requireContext(),
                object : AdapterDetailsLocations.OnClickEventDetailsLocationRv {
                    override fun onClickHero(heroEntity: HeroEntity) {
                        parentFragmentManager.beginTransaction().replace(
                            R.id.containerFragments,
                            DetailsHeroesFragment.newInstance(hero = heroEntity)
                        ).addToBackStack(null)
                            .commit()
                    }
                }, object : AdapterDetailsLocations.OnHolderEventListener {
                    override fun onHideProgressBar() {
                        mBinding.progressBarRecyclerDetailsLocation.visibility = View.GONE
                    }

                })

        setupFragment()

        mBinding.detailsLocationsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        mBinding.detailsLocationsRecyclerView.adapter = mAdapterDetailsLocationsHeroes

        getNumberHeroes(mLocation).forEach {
            viewModel.getSingleHero(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            mAdapterDetailsLocationsHeroes.setData(it)
        }
    }

    private fun setupFragment() {
        mBinding.DetailsLocationName.text = mLocation.name

        mBinding.DetailsLocationType.text = mLocation.type

        mBinding.DetailsLocationDimension.text = mLocation.dimension
    }

    private fun getNumberHeroes(locationEntity: LocationEntity): List<Int> {
        val listId = mutableListOf<Int>()

        locationEntity.residents.forEach {
            val list = it.split("/")
            listId.add(list[list.size - 1].toInt())
        }

        return listId
    }


    interface OnClickBackDetailsLocation {
        fun onClickDetailsLocation()
    }

    interface OnHideShowBottomNavigationFilterLocation {
        fun onHideLocationDetails()
    }

    companion object {

        private const val KEY_BUNDLE_LOCATION = "KEY_BUNDLE_LOCATION"

        fun newInstance(locationEntity: LocationEntity) =
            DetailsLocationsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_BUNDLE_LOCATION, locationEntity)
                }
            }
    }
}