package com.example.rickyandmorty_finalproject_anderson.presentation.locations.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.databinding.FilterDialogLocationsBinding

class FilterDialogLocations : DialogFragment() {

    private lateinit var listener: EventListenerLocations
    private lateinit var binding: FilterDialogLocationsBinding

    private var mType = ""
    private var mDimension = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = parentFragment as EventListenerLocations

        mType = arguments?.getString(LocationsFragment.TYPE_EXTRA, "")!!
        mDimension = arguments?.getString(LocationsFragment.DIMENSION_EXTRA, "")!!

    }

    override fun onResume() {
        super.onResume()

        val type = resources.getStringArray(R.array.typeLocationsArray)
        val arrayAdapterType = ArrayAdapter(requireContext(), R.layout.drop_down_item, type)
        binding.materialSpinnerLocationsType.setAdapter(arrayAdapterType)
        if (mType.isNotEmpty()) {
            binding.materialSpinnerLocationsType.setText(mType, false)
        }

        val dimension = resources.getStringArray(R.array.dimensionLocationsArray)
        val arrayAdapterDimension =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, dimension)
        binding.materialSpinnerLocationDimension.setAdapter(arrayAdapterDimension)
        if (mDimension.isNotEmpty()) {
            binding.materialSpinnerLocationDimension.setText(mDimension, false)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FilterDialogLocationsBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setTitle("Choose filters")
            .setMessage("available filters")
            .setIcon(R.drawable.rocket_ship_location)
            .setView(binding.root)
            .setPositiveButton(
                getString(R.string.apply)
            ) { dialog, which -> listener.onClickOk(getFilters()) }
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, which -> listener.onClickCancel() }
            .setNeutralButton(
                getString(R.string.remove_filters)
            ) { dialog, which -> listener.onClickClear() }
            .create()
    }

    private fun getFilters() = Bundle().apply {
        putString(KEY_TYPE_BUNDLE, binding.materialSpinnerLocationsType.editableText.toString())
        putString(
            KEY_DIMENSION_BUNDLE,
            binding.materialSpinnerLocationDimension.editableText.toString()
        )
    }

    interface EventListenerLocations {
        fun onClickOk(bundle: Bundle)
        fun onClickClear()
        fun onClickCancel()
    }

    companion object {

        const val KEY_TYPE_BUNDLE = "KEY_TYPE_BUNDLE"
        const val KEY_DIMENSION_BUNDLE = "KEY_DIMENSION_BUNDLE"

        fun newInstance(bundle: Bundle) = FilterDialogLocations().apply {
            arguments = bundle
        }
    }
}