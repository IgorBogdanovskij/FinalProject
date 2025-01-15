package com.example.rickyandmorty.presentation.episodes.ui.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickyandmorty.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty.databinding.ItemEpisodeBinding

class AdapterEpisodes(
    val context: Context,
    private val callback: OnLoadNextPage,
    private val mOnCLickHolderEpisodes: OnCLickHolderEpisodes,
    private val onHolderEventListener: OnHolderEventListener,
) :
    RecyclerView.Adapter<AdapterEpisodes.ViewHolder>() {


    private var mListEpisodes = listOf<EpisodeEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mListEpisodes[position])

        onHolderEventListener.onHideProgressBar()
    }

    override fun getItemCount() = mListEpisodes.size

    fun setData(list: List<EpisodeEntity>) {
        mListEpisodes = list
        notifyDataSetChanged()
    }

    interface OnLoadNextPage {
        fun onLoad()
    }

    interface OnHolderEventListener {
        fun onHideProgressBar()
    }

    interface OnCLickHolderEpisodes {
        fun onClick(episodeEntity: EpisodeEntity)
    }

    inner class ViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                mOnCLickHolderEpisodes.onClick(mListEpisodes[adapterPosition])
            }
        }

        fun bind(episodeEntity: EpisodeEntity) {

            if ((adapterPosition >= itemCount - 1 && itemCount >= 20 && itemCount < 42))
                callback.onLoad()

            binding.nameEpisode.text = episodeEntity.name
            binding.numberEpisode.text = episodeEntity.episode
            binding.dataRelizeEpisode.text = episodeEntity.air_date
        }
    }
}