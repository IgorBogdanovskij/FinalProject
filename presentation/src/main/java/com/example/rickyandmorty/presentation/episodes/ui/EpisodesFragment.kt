package com.example.rickyandmorty.presentation.episodes.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.App
import com.example.rickyandmorty.R
import com.example.rickyandmorty.databinding.FragmentEpisodsBinding
import com.example.rickyandmorty.presentation.detailsEpisodes.ui.DetailsEpisodesFragment
import com.example.rickyandmorty.presentation.episodes.EpisodesFragmentViewModel
import com.example.rickyandmorty.presentation.episodes.ui.recycler.AdapterEpisodes
import com.example.rickyandmorty.presentation.utils.consume
import com.example.rickyandmorty.presentation.utils.toUpperCaseFromList
import javax.inject.Inject


class EpisodesFragment : Fragment(), FilterDialogEpisodes.EventListener {

    private var episode = ""

    private var isFiltering = false

    private var isSearching = false

    private var pageAllEpisodes = 1

    private var mListData = mutableSetOf<EpisodeEntity>()

    private lateinit var mEpisodesBinding: FragmentEpisodsBinding

    private lateinit var mAdapterEpisodes: AdapterEpisodes

    private lateinit var mGridLayoutManager: GridLayoutManager

    private lateinit var mOnSetSupportActionBarEpisodes: OnSetSupportActionBarEpisodes

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EpisodesFragmentViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mOnSetSupportActionBarEpisodes = context as OnSetSupportActionBarEpisodes
        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .episodeComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mEpisodesBinding = FragmentEpisodsBinding.inflate(layoutInflater)
        return mEpisodesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        mOnSetSupportActionBarEpisodes.onSetActionBarEpisodes(mEpisodesBinding.toolbarEpisodes)

        mGridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        mAdapterEpisodes =
            AdapterEpisodes(requireContext(), object : AdapterEpisodes.OnLoadNextPage {
                override fun onLoad() {
                    if (isFiltering) {

                    } else if (!isFiltering && !isSearching) {
                        pageAllEpisodes++
                        viewModel.getNextPage(page = pageAllEpisodes)
                    } else if (isSearching) {

                    }
                }
            }, object : AdapterEpisodes.OnCLickHolderEpisodes {
                override fun onClick(episodeEntity: EpisodeEntity) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.containerFragments,
                            DetailsEpisodesFragment.newInstance(episodeEntity = episodeEntity)
                        ).addToBackStack(null)
                        .commit()
                }
            }, object : AdapterEpisodes.OnHolderEventListener {
                override fun onHideProgressBar() {
                    mEpisodesBinding.progressBarRecyclerEpisode.visibility = View.GONE
                }
            })

        viewModel.liveData.observe(viewLifecycleOwner) {
            mListData.addAll(it)
            mAdapterEpisodes.setData(mListData.toList())
        }

        viewModel.liveDataFilters.observe(viewLifecycleOwner, {
            mListData.clear()
            mListData.add(it)
            mAdapterEpisodes.setData(mListData.toList())
        })

        viewModel.liveDataSearch.observe(viewLifecycleOwner, {
            mListData.clear()
            mListData.addAll(it)
            mAdapterEpisodes.setData(mListData.toList())
        })

        viewModel.liveDataFiltersEpisode.observe(viewLifecycleOwner, {
            episode = it
        })

        viewModel.isSearchResultEmptyEvent.consume(viewLifecycleOwner) {

            if (it) {
                mEpisodesBinding.recyclerViewEpisodes.visibility = View.GONE
                mEpisodesBinding.emptySearchOrFilterImageEpisodes.visibility = View.VISIBLE
            } else {
                mEpisodesBinding.recyclerViewEpisodes.visibility = View.VISIBLE
                mEpisodesBinding.emptySearchOrFilterImageEpisodes.visibility = View.GONE
            }
        }

        viewModel.isLoadingEvent.consume(viewLifecycleOwner) {
            if (it && pageAllEpisodes < 4) {
                mEpisodesBinding.recyclerViewEpisodes.visibility = View.GONE
                mEpisodesBinding.progressBarRecyclerEpisode.visibility = View.VISIBLE
            } else {
                mEpisodesBinding.recyclerViewEpisodes.visibility = View.VISIBLE
                mEpisodesBinding.progressBarRecyclerEpisode.visibility = View.GONE
            }
        }

        mEpisodesBinding.recyclerViewEpisodes.layoutManager = mGridLayoutManager

        mEpisodesBinding.recyclerViewEpisodes.adapter = mAdapterEpisodes

        mEpisodesBinding.refreshEpisodes.apply {
            setOnRefreshListener {
                mListData.clear()
                pageAllEpisodes = 1
                viewModel.getNextPage(page = pageAllEpisodes)
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
                    mEpisodesBinding.toolbarEpisodes.menu.findItem(R.id.searchItem).actionView as SearchView

                searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        val strForSearch = newText.toUpperCaseFromList(newText.split(" "))

                        if (strForSearch.isNotEmpty()) {
                            pageAllEpisodes = 1
                            mListData.clear()
                            isSearching = true
                            viewModel.getEpisodeBySearch(name = strForSearch)
                        } else {
                            mEpisodesBinding.emptySearchOrFilterImageEpisodes.visibility = View.GONE
                            pageAllEpisodes = 1
                            isSearching = false
                            mListData.clear()
                            viewModel.getNextPage(pageAllEpisodes)
                        }
                        return true
                    }
                })
            }
            R.id.filter -> {
                FilterDialogEpisodes.newInstance(bundleOf(EPISODE_EXTRA to episode))
                    .show(childFragmentManager, null)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickOk(bundle: Bundle) {

        mListData.clear()

        isFiltering = true

        episode = if (bundle.getString(FilterDialogEpisodes.KEY_BUNDLE_EPISODE) == "no filter") {
            ""
        } else {
            bundle.getString(FilterDialogEpisodes.KEY_BUNDLE_EPISODE)!!
        }

        Toast.makeText(requireContext(), "Set filters:\nType - $episode", Toast.LENGTH_SHORT).show()

        viewModel.setFilters(episode.replaceFirstChar { it.uppercase() })
    }

    override fun onClickClear() {
        Toast.makeText(
            requireContext(),
            "All filters removed, set a default list",
            Toast.LENGTH_SHORT
        ).show()
        mListData.clear()
        isFiltering = false

        episode = ""

        viewModel.getNextPage(pageAllEpisodes)
    }

    override fun onClickCancel() {
    }

    interface OnSetSupportActionBarEpisodes {
        fun onSetActionBarEpisodes(toolbar: Toolbar)
    }

    companion object {
        const val EPISODE_EXTRA = "EPISODE_EXTRA"

        fun newInstance() = EpisodesFragment()
    }
}
