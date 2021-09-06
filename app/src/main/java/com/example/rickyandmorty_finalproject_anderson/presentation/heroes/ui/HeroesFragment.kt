package com.example.rickyandmorty_finalproject_anderson.presentation.heroes.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.FragmentCharactersBinding
import com.example.rickyandmorty_finalproject_anderson.App
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.DetailsHeroesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.heroes.HeroesFragmentViewModel
import com.example.rickyandmorty_finalproject_anderson.presentation.heroes.ui.recycler.AdapterHeroes
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.consume
import com.example.rickyandmorty_finalproject_anderson.presentation.utils.toUpperCaseFromList
import javax.inject.Inject


class HeroesFragment : Fragment(), FilterDialogHeroes.EventListener {

    private var species: String = ""
    private var gender: String = ""
    private var status: String = ""

    private var pageForFilters = 1
    private var pageAllHeroes = 1
    private var pageForSearch = 1

    private var mSearchText = ""

    private var isSearching: Boolean = false
    private var isFiltering: Boolean = false

    private var mListData = mutableSetOf<HeroEntity>()

    private lateinit var mCharactersBinding: FragmentCharactersBinding
    private lateinit var mAdapterHeroes: AdapterHeroes

    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var mOnSetSupportActionBarEpisodes: OnSetSupportActionBarHeroes

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HeroesFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mOnSetSupportActionBarEpisodes = context as OnSetSupportActionBarHeroes
        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .heroComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCharactersBinding = FragmentCharactersBinding.inflate(layoutInflater)
        return mCharactersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        mOnSetSupportActionBarEpisodes.onSetActionBarHeroes(mCharactersBinding.toolbarCharacters)

        mGridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        mAdapterHeroes = AdapterHeroes(requireContext(), object : AdapterHeroes.OnLoadNextPage {
            override fun onLoad() {
                if (isSearching) {
                    pageForSearch++
                    viewModel.getHeroesBySearch(pageForSearch, mSearchText)
                } else if (!isSearching && !isFiltering) {
                    pageAllHeroes++
                    viewModel.getHeroesByPage(page = pageAllHeroes)
                } else if (isFiltering) {
                    pageForFilters++
                    viewModel.setFilters(pageForFilters, status, species, gender)
                }
            }
        }, object : AdapterHeroes.OnCLickHolder {
            override fun onClick(hero: HeroEntity) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.containerFragments,
                    DetailsHeroesFragment.newInstance(hero = hero)
                ).addToBackStack(null)
                    .commit()
            }
        }, object : AdapterHeroes.OnHolderEventListener {
            override fun onHideProgressBar() {
                mCharactersBinding.progressBarRecyclerHero.visibility = View.GONE
            }
        })

        viewModel.liveData.observe(viewLifecycleOwner) {

            mListData.addAll(it)
            mAdapterHeroes.setData(mListData.toList())

        }

        viewModel.liveDataFiltersStatus.observe(viewLifecycleOwner, {
            status = it
        })

        viewModel.liveDataFiltersGender.observe(viewLifecycleOwner, {
            gender = it
        })

        viewModel.liveDataFiltersSpecies.observe(viewLifecycleOwner, {
            species = it
        })

        viewModel.isSearchResultEmptyEvent.consume(viewLifecycleOwner) {

            if (it) {
                mCharactersBinding.recyclerViewChapters.visibility = View.GONE
                mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.VISIBLE
            } else {
                mCharactersBinding.recyclerViewChapters.visibility = View.VISIBLE
                mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.GONE
            }
        }

        viewModel.isLoadingEvent.consume(viewLifecycleOwner) {
            if (it) {
                mCharactersBinding.recyclerViewChapters.visibility = View.GONE
                mCharactersBinding.progressBarRecyclerHero.visibility = View.VISIBLE
            } else {
                mCharactersBinding.recyclerViewChapters.visibility = View.VISIBLE
                mCharactersBinding.progressBarRecyclerHero.visibility = View.GONE
            }
        }

        viewModel.isSearchResultEmptyEventFilters.consume(viewLifecycleOwner){
            if (it) {
                mCharactersBinding.recyclerViewChapters.visibility = View.GONE
                mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.VISIBLE
            } else {
                mCharactersBinding.recyclerViewChapters.visibility = View.VISIBLE
                mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.GONE
            }
        }

        mCharactersBinding.recyclerViewChapters.layoutManager = mGridLayoutManager

        mCharactersBinding.recyclerViewChapters.adapter = mAdapterHeroes

        mCharactersBinding.refreshCharacters.apply {
            setOnRefreshListener {
                pageAllHeroes = 1
                viewModel.getHeroesByPage(page = pageAllHeroes)
                mListData.clear()
                Handler(Looper.getMainLooper()).postDelayed({
                    isRefreshing = false
                }, 2000)
                Toast.makeText(requireContext(), "Refresh", Toast.LENGTH_SHORT).show()
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
                    mCharactersBinding.toolbarCharacters.menu.findItem(R.id.searchItem).actionView as SearchView

                searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        val strForSearch = newText.toUpperCaseFromList(newText.split(" "))

                        if (strForSearch.isNotEmpty()) {
                            pageForSearch = 1
                            mListData.clear()
                            isSearching = true
                            mSearchText = strForSearch
                            viewModel.getHeroesBySearch(page = pageForSearch, name = strForSearch)
                        } else {
                            mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.GONE
                            pageForSearch = 1
                            isSearching = false
                            pageAllHeroes = 1
                            mListData.clear()
                            viewModel.getHeroesByPage(pageAllHeroes)
                        }
                        return true
                    }
                })
            }
            R.id.filter -> {
                val bundle = Bundle().apply {
                    putString(SPECIES_EXTRA, species)
                    putString(GENDER_EXTRA, gender)
                    putString(STATUS_EXTRA, status)
                }

                FilterDialogHeroes.newInstance(bundle = bundle).show(childFragmentManager, null)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickOk(bundle: Bundle) {

        pageForFilters = 1

        mListData.clear()

        isFiltering = true

        status = if (bundle.getString(FilterDialogHeroes.KEY_STATUS_BUNDLE) == "no filter") {
            ""
        } else {
            bundle.getString(FilterDialogHeroes.KEY_STATUS_BUNDLE)!!
        }

        species = if (bundle.getString(FilterDialogHeroes.KEY_SPECIES_BUNDLE) == "no filter") {
            ""
        } else {
            bundle.getString(FilterDialogHeroes.KEY_SPECIES_BUNDLE)!!
        }

        gender = if (bundle.getString(FilterDialogHeroes.KEY_GENDER_BUNDLE) == "no filter") {
            ""
        } else {
            bundle.getString(FilterDialogHeroes.KEY_GENDER_BUNDLE)!!
        }

        Toast.makeText(
            requireContext(),
            "Set filters:\nstatus - $status\nspecies - $species\ngender - $gender",
            Toast.LENGTH_SHORT
        ).show()

        viewModel.setFilters(
            pageForFilters,
            status.replaceFirstChar { it.uppercase() } ?: "",
            species.replaceFirstChar { it.uppercase() } ?: "",
            gender.replaceFirstChar { it.uppercase() } ?: "")
    }

    override fun onClickClear() {

        mCharactersBinding.emptySearchOrFilterImageHeroes.visibility = View.GONE

        Toast.makeText(
            requireContext(),
            "All filters removed, set a default list",
            Toast.LENGTH_SHORT
        ).show()

        mListData.clear()

        species = ""
        status = ""
        gender = ""

        isFiltering = false

        pageAllHeroes = 1

        viewModel.getHeroesByPage(pageAllHeroes)
    }

    override fun onClickCancel() {

    }

    interface OnSetSupportActionBarHeroes {
        fun onSetActionBarHeroes(toolbar: Toolbar)
    }

    companion object {

        const val SPECIES_EXTRA = "SPECIES_EXTRA"
        const val GENDER_EXTRA = "SPECIES_GENDER"
        const val STATUS_EXTRA = "SPECIES_STATUS"

        fun newInstance() = HeroesFragment()
    }
}