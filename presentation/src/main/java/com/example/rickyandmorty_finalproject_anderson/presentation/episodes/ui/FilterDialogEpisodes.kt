package com.example.rickyandmorty_finalproject_anderson.presentation.episodes.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.databinding.FilterDialogEpisodesBinding

class FilterDialogEpisodes : DialogFragment() {

    private lateinit var mEventListener: EventListener
    private lateinit var binding: FilterDialogEpisodesBinding

    private var mEpisode = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mEventListener = parentFragment as EventListener

        mEpisode = arguments?.getString(EpisodesFragment.EPISODE_EXTRA)!!
    }

    override fun onResume() {
        super.onResume()

        val episode = resources.getStringArray(R.array.episodesEpisodeArray)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, episode)
        binding.materialSpinnerEpisodeEpisodes.setAdapter(arrayAdapter)


        if (mEpisode.isNotEmpty()) {
            binding.materialSpinnerEpisodeEpisodes.setText(mEpisode, false)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FilterDialogEpisodesBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setTitle("Choose filters")
            .setMessage("available filters")
            .setIcon(R.drawable.rocket_ship)
            .setView(binding.root)
            .setPositiveButton(
                getString(R.string.apply)
            ) { dialog, which -> mEventListener.onClickOk(getFilters()) }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, which -> mEventListener.onClickCancel() }
            .setNeutralButton(
                getString(R.string.remove_filters)
            ) { dialog, which -> mEventListener.onClickClear() }
            .create()
    }

    private fun getFilters() = Bundle().apply {
        putString(
            KEY_BUNDLE_EPISODE,
            binding.materialSpinnerEpisodeEpisodes.editableText.toString()
        )
    }

    interface EventListener {
        fun onClickOk(bundle: Bundle)
        fun onClickClear()
        fun onClickCancel()
    }

    companion object {

        const val KEY_BUNDLE_EPISODE = "KEY_BUNDLE_EPISODE"

        fun newInstance(bundle: Bundle) = FilterDialogEpisodes().apply {
            arguments = bundle
        }
    }
}