package com.example.rickyandmorty.presentation.heroes.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.rickyandmorty.R
import com.example.rickyandmorty.databinding.FilterDialogHeroesBinding

class FilterDialogHeroes : DialogFragment() {

    private lateinit var mEventListener: EventListener
    private lateinit var binding: FilterDialogHeroesBinding

    private var mStatus = ""
    private var mGender = ""
    private var mSpecies = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mEventListener = parentFragment as EventListener

        mStatus = arguments?.getString(HeroesFragment.STATUS_EXTRA, "")!!
        mGender = arguments?.getString(HeroesFragment.GENDER_EXTRA, "")!!
        mSpecies = arguments?.getString(HeroesFragment.SPECIES_EXTRA, "")!!

    }



    override fun onResume() {
        super.onResume()

        val status = resources.getStringArray(R.array.statusHeroArray)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, status)
        binding.materialSpinnerHeroesStatus.setAdapter(arrayAdapter)

        if (mStatus.isNotEmpty()) {
            binding.materialSpinnerHeroesStatus.setText(mStatus, false)
        }

        val species = resources.getStringArray(R.array.speciesHeroArray)
        val arrayAdapterSpecies = ArrayAdapter(requireContext(), R.layout.drop_down_item, species)
        binding.materialSpinnerHeroesSpecies.setAdapter(arrayAdapterSpecies)

        if (mSpecies.isNotEmpty()){
            binding.materialSpinnerHeroesSpecies.setText(mSpecies, false)
        }

        val gender = resources.getStringArray(R.array.genderHeroArray)
        val arrayAdapterGender = ArrayAdapter(requireContext(), R.layout.drop_down_item, gender)
        binding.materialSpinnerHeroesGender.setAdapter(arrayAdapterGender)

        if (mGender.isNotEmpty()){
            binding.materialSpinnerHeroesGender.setText(mGender, false)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FilterDialogHeroesBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setTitle("Choose filters")
            .setMessage("available filters")
            .setIcon(R.drawable.ricky_morty_launch)
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
        putString(KEY_STATUS_BUNDLE, binding.materialSpinnerHeroesStatus.editableText.toString())
        putString(KEY_SPECIES_BUNDLE, binding.materialSpinnerHeroesSpecies.editableText.toString())
        putString(KEY_GENDER_BUNDLE, binding.materialSpinnerHeroesGender.editableText.toString())
    }

    interface EventListener {
        fun onClickOk(bundle: Bundle)
        fun onClickClear()
        fun onClickCancel()
    }

    companion object {

        const val KEY_STATUS_BUNDLE = "KEY_STATUS_BUNDLE"
        const val KEY_SPECIES_BUNDLE = "KEY_SPECIES_BUNDLE"
        const val KEY_GENDER_BUNDLE = "KEY_GENDER_BUNDLE"

        fun newInstance(bundle: Bundle) = FilterDialogHeroes().apply {
            arguments = bundle
        }
    }
}