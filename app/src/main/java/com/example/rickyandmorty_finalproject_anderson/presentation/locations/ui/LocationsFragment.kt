package com.example.rickyandmorty_finalproject_anderson.presentation.locations.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty_finalproject_anderson.App
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.FragmentLocationsBinding
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui.DetailsLocationsFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.locations.LocationsFragmentViewModel
import com.example.rickyandmorty_finalproject_anderson.presentation.locations.ui.recycler.AdapterLocations
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.consume
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.toUpperCaseFromList
import javax.inject.Inject


class LocationsFragment : Fragment(), FilterDialogLocations.EventListenerLocations {

    private var dimension = ""
    private var type = ""

    private var isFiltering = false
    private var isSearching = false
    private var pageForFilters = 1

    private var mListData = mutableSetOf<LocationEntity>()
    private lateinit var mLocationsBinding: FragmentLocationsBinding

    private var pageLocations = 1
    private lateinit var mAdapterLocations: AdapterLocations

    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var mOnSetSupportActionBarEpisodes: OnSetSupportActionBarLocations

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LocationsFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mOnSetSupportActionBarEpisodes = context as OnSetSupportActionBarLocations
        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .locationComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLocationsBinding = FragmentLocationsBinding.inflate(layoutInflater)
        return mLocationsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        mOnSetSupportActionBarEpisodes.onSetActionBarLocations(mLocationsBinding.toolbarLocation)

        mGridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        mAdapterLocations =
            AdapterLocations(requireContext(), object : AdapterLocations.OnLoadNextPage {
                override fun onLoad() {
                    if (isSearching) {

                    } else if (!isSearching && !isFiltering) {

                        pageLocations++
                        viewModel.getLocationsByPage(page = pageLocations)
                    } else if (isFiltering) {
                        pageForFilters++
                        viewModel.setFilters(pageForFilters, type, dimension)
                    }
                }
            }, object : AdapterLocations.OnCLickHolder {
                override fun onClick(locationEntity: LocationEntity) {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.containerFragments,
                        DetailsLocationsFragment.newInstance(locationEntity)
                    ).addToBackStack(null)
                        .commit()
                }
            }, object : AdapterLocations.OnHolderEventListener {
                override fun onHideProgressBar() {
                    mLocationsBinding.progressBarRecyclerLocation.visibility = View.GONE
                }

            })

        viewModel.liveData.observe(viewLifecycleOwner) {
            mListData.addAll(it)
            mAdapterLocations.setData(mListData.toList())
        }

        viewModel.liveDataFilters.observe(viewLifecycleOwner, {
            Log.d("lo", "onViewCreated: ${it.size}")
            mListData.addAll(it)
            mAdapterLocations.setData(mListData.toList())
        })

        viewModel.liveDataFiltersType.observe(viewLifecycleOwner, {
            type = it
        })

        viewModel.liveDataFiltersDimension.observe(viewLifecycleOwner, {
            dimension = it
        })

        viewModel.isSearchResultEmptyEvent.consume(viewLifecycleOwner) {
            if (it) {
                mLocationsBinding.recyclerViewLocations.visibility = View.GONE
                mLocationsBinding.emptySearchOrFilterImageLocations.visibility = View.VISIBLE
            } else {
                mLocationsBinding.recyclerViewLocations.visibility = View.VISIBLE
                mLocationsBinding.emptySearchOrFilterImageLocations.visibility = View.GONE
            }
        }

        viewModel.isLoadingEvent.consume(viewLifecycleOwner) {
            if (it && pageLocations < 7) {
                mLocationsBinding.recyclerViewLocations.visibility = View.GONE
                mLocationsBinding.progressBarRecyclerLocation.visibility = View.VISIBLE
            } else {
                mLocationsBinding.recyclerViewLocations.visibility = View.VISIBLE
                mLocationsBinding.progressBarRecyclerLocation.visibility = View.GONE
            }
        }

        viewModel.isSearchResultEmptyFilters.consume(viewLifecycleOwner) {
            if (it) {
                mLocationsBinding.recyclerViewLocations.visibility = View.GONE
                mLocationsBinding.emptySearchOrFilterImageLocations.visibility = View.VISIBLE
            } else {
                mLocationsBinding.recyclerViewLocations.visibility = View.VISIBLE
                mLocationsBinding.emptySearchOrFilterImageLocations.visibility = View.GONE
            }
        }

        mLocationsBinding.recyclerViewLocations.layoutManager = mGridLayoutManager

        mLocationsBinding.recyclerViewLocations.adapter = mAdapterLocations

        mLocationsBinding.refreshLocations.apply {
            setOnRefreshListener {
                mListData.clear()
                pageLocations = 1
                viewModel.getLocationsByPage(page = pageLocations)
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                }, 1000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            R.id.searchItem -> {
                val searchItem =
                    mLocationsBinding.toolbarLocation.menu.findItem(R.id.searchItem).actionView as SearchView

                searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        val strForSearch = newText.toUpperCaseFromList(newText.split(" "))

                        if (strForSearch.isNotEmpty()) {
                            pageLocations = 1
                            mListData.clear()
                            isSearching = true
                            viewModel.getEpisodeBySearch(name = strForSearch)
                        } else {
                            mLocationsBinding.emptySearchOrFilterImageLocations.visibility =
                                View.GONE
                            pageLocations = 1
                            isSearching = false
                            mListData.clear()
                            viewModel.getLocationsByPage(pageLocations)
                        }
                        return true
                    }
                })
            }
            R.id.filter -> {
                val bundle = Bundle().apply {
                    putString(TYPE_EXTRA, type)
                    putString(DIMENSION_EXTRA, dimension)
                }
                FilterDialogLocations.newInstance(bundle).show(childFragmentManager, null)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickOk(bundle: Bundle) {
        pageForFilters = 1

        mListData.clear()

        isFiltering = true

        type = if (bundle.getString(FilterDialogLocations.KEY_TYPE_BUNDLE) == "no filter") {
            ""
        } else {
            bundle.getString(FilterDialogLocations.KEY_TYPE_BUNDLE)!!
        }

        dimension =
            if (bundle.getString(FilterDialogLocations.KEY_DIMENSION_BUNDLE) == "no filter") {
                ""
            } else {
                bundle.getString(FilterDialogLocations.KEY_DIMENSION_BUNDLE)!!
            }

        Toast.makeText(
            requireContext(),
            "Set filters:\nType - $type\nDimension - $dimension",
            Toast.LENGTH_SHORT
        ).show()

        viewModel.setFilters(
            pageForFilters,
            type.replaceFirstChar { it.uppercase() } ?: "",
            dimension.replaceFirstChar { it.uppercase() } ?: "")
    }

    override fun onClickClear() {

        mLocationsBinding.emptySearchOrFilterImageLocations.visibility = View.GONE

        Toast.makeText(
            requireContext(),
            "All filters removed, set a default list",
            Toast.LENGTH_SHORT
        ).show()
        mListData.clear()
        type = ""
        dimension = ""
        isFiltering = false
        pageForFilters = 1
        viewModel.getLocationsByPage(pageLocations)
    }

    override fun onClickCancel() {

    }

    interface OnSetSupportActionBarLocations {
        fun onSetActionBarLocations(toolbar: Toolbar)
    }

    companion object {

        const val TYPE_EXTRA = "TYPE_EXTRA"
        const val DIMENSION_EXTRA = "DIMENSION_EXTRA"

        fun newInstance() = LocationsFragment()
    }
}


//val searchText = newText?.lowercase(Locale.getDefault())
//val listCopy = mListData.toMutableList()
//
//val tempList: List<LocationEntity> = if (searchText!!.isNotEmpty()) {
//    mListData.filter {
//        it.name.lowercase(Locale.getDefault())
//            .contains(searchText) || it.type.lowercase(Locale.getDefault())
//            .contains(searchText) || it.dimension.lowercase(Locale.getDefault())
//            .contains(searchText)
//    }.map {
//        it
//    }
//} else {
//    mListData.toList()
//}
//
//listCopy.clear()
//listCopy.addAll(tempList)
//
//mAdapterLocations.setData(listCopy)