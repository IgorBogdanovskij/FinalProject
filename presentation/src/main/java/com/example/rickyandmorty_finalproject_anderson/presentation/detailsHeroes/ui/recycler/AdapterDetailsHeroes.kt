package com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.ItemEpisodeBinding

class AdapterDetailsHeroes(
    private val callback: OnClickEventDetailsHeroesRv,
    private val onHolderEventListener: OnHolderEventListener
) :
    RecyclerView.Adapter<AdapterDetailsHeroes.ViewHolder>() {

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
        //val diff = DiffUtilsCallbackEpisodesDetails(mListEpisodes, list)
        //val diffResult = DiffUtil.calculateDiff(diff)
        mListEpisodes = list
        //diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()

    }

    interface OnClickEventDetailsHeroesRv {
        fun onClickEpisode(episodeEntity: EpisodeEntity)
    }

    interface OnHolderEventListener {
        fun onHideProgressBar()
    }

    inner class ViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { callback.onClickEpisode(mListEpisodes[adapterPosition]) }
        }

        fun bind(episodeEntity: EpisodeEntity) {
            binding.nameEpisode.text = episodeEntity.name
            binding.numberEpisode.text = episodeEntity.episode
            binding.dataRelizeEpisode.text = episodeEntity.air_date
        }
    }
}