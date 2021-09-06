package com.example.rickyandmorty_finalproject_anderson.presentation.locations.ui.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.location.LocationEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.ItemLocationBinding

class AdapterLocations(
    val context: Context,
    private val callback: OnLoadNextPage,
    private val mOnClickHolder: OnCLickHolder,
    private val onHolderEventListener: OnHolderEventListener
) :
    RecyclerView.Adapter<AdapterLocations.ViewHolder>() {

    private var mListLocations = listOf<LocationEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mListLocations[position])

        onHolderEventListener.onHideProgressBar()
    }

    override fun getItemCount() = mListLocations.size

    fun setData(list: List<LocationEntity>) {
        mListLocations = list
        notifyDataSetChanged()
    }

    interface OnLoadNextPage {
        fun onLoad()
    }

    interface OnHolderEventListener {
        fun onHideProgressBar()
    }

    interface OnCLickHolder {
        fun onClick(locationEntity: LocationEntity)
    }

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { mOnClickHolder.onClick(mListLocations[adapterPosition]) }
        }

        fun bind(locationEntity: LocationEntity) {

            if ((adapterPosition >= itemCount - 1 && itemCount > 19))
                callback.onLoad()

            binding.nameLocation.text = locationEntity.name
            binding.typeLocation.text = locationEntity.type
            binding.dimensionLocation.text = locationEntity.dimension
        }
    }
}