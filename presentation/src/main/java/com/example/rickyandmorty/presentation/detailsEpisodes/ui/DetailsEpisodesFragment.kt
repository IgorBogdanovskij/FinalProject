package com.example.rickyandmorty.presentation.detailsEpisodes.ui

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
import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.domain.models.hero.HeroEntity
import com.example.rickyandmorty.App
import com.example.rickyandmorty.R
import com.example.rickyandmorty.databinding.FragmentDetailsEpisodesBinding
import com.example.rickyandmorty.presentation.detailsEpisodes.DetailsEpisodesFragmentViewModel
import com.example.rickyandmorty.presentation.detailsEpisodes.ui.recycler.AdapterDetailsEpisodes
import com.example.rickyandmorty.presentation.detailsHeroes.ui.DetailsHeroesFragment
import javax.inject.Inject


class DetailsEpisodesFragment : Fragment(R.layout.fragment_details_episodes) {

    private lateinit var mAdapterDetailsEpisodesHeroes: AdapterDetailsEpisodes

    private lateinit var mBinding: FragmentDetailsEpisodesBinding
    private lateinit var mEpisodeEntity: EpisodeEntity

    private lateinit var mOnHideShowBottomNavigationFilterEpisodes: OnHideShowBottomNavigationFilterEpisodes
    private lateinit var mOnClickBackDetailsEpisodes: OnClickBackDetailsEpisodes

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailsEpisodesFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mOnClickBackDetailsEpisodes = context as OnClickBackDetailsEpisodes

        mOnHideShowBottomNavigationFilterEpisodes =
            context as OnHideShowBottomNavigationFilterEpisodes

//        mEpisodeEntity = arguments?.getParcelable(KEY_BUNDLE_EPISODE)

        initDagger()
    }

    private fun initDagger() {
        (requireActivity().application as App).appComponent
            .detailsEpisodeComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsEpisodesBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mOnHideShowBottomNavigationFilterEpisodes.onHideEpisodesDetails()

        mBinding.toolbarDetailsEpisodes.navigationIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_back_24)

        mBinding.toolbarDetailsEpisodes.setOnClickListener { mOnClickBackDetailsEpisodes.onClickDetailsEpisodes() }

        setupFragment()

        mAdapterDetailsEpisodesHeroes =
            AdapterDetailsEpisodes(
                requireContext(),
                object : AdapterDetailsEpisodes.OnClickEventDetailsEpisodesRv {
                    override fun onClickHero(heroEntity: HeroEntity) {
                        parentFragmentManager.beginTransaction().replace(
                            R.id.containerFragments,
                            DetailsHeroesFragment.newInstance(heroEntity)
                        ).addToBackStack(null)
                            .commit()
                    }
                }, object : AdapterDetailsEpisodes.OnHolderEventListener {
                    override fun onHideProgressBar() {
                        mBinding.progressBarRecyclerEpisodeDetails.visibility = View.GONE
                    }

                })

        mBinding.detailsEpisodesRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        mBinding.detailsEpisodesRecyclerView.adapter = mAdapterDetailsEpisodesHeroes

        getNumberHeroes(mEpisodeEntity).forEach {
            viewModel.getSingleHero(it)
        }


        viewModel.liveData.observe(viewLifecycleOwner) {
            mAdapterDetailsEpisodesHeroes.setData(it)
        }
    }

    private fun setupFragment() {

        mBinding.DetailsNameEpisode.text = mEpisodeEntity.name

        mBinding.DetailsReleaseDataEpisode.text = mEpisodeEntity.air_date

    }

    private fun getNumberHeroes(episodeEntity: EpisodeEntity): List<Int> {
        val listId = mutableListOf<Int>()

        episodeEntity.characters.forEach {
            val list = it.split("/")
            listId.add(list[list.size - 1].toInt())
        }

        return listId
    }

    interface OnClickBackDetailsEpisodes {
        fun onClickDetailsEpisodes()
    }

    interface OnHideShowBottomNavigationFilterEpisodes {
        fun onHideEpisodesDetails()
    }

    companion object {

        private const val KEY_BUNDLE_EPISODE = "KEY_BUNDLE_EPISODE"

        fun newInstance(episodeEntity: EpisodeEntity): DetailsEpisodesFragment {
            return DetailsEpisodesFragment().apply {

//                val bundle = Bundle().apply {
//                    putParcelable(KEY_BUNDLE_EPISODE, episodeEntity)
//                }
//                arguments = bundle

            }
        }
    }
}