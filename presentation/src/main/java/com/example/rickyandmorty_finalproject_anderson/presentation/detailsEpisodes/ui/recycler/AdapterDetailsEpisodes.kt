package com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.ui.recycler

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.domain.models.hero.HeroEntity
import com.example.rickyandmorty_finalproject_anderson.databinding.ItemHeroBinding

class AdapterDetailsEpisodes(
    private val context: Context,
    private val mOnClickEventDetailsHeroes: OnClickEventDetailsEpisodesRv,
    private val onHolderEventListener: OnHolderEventListener
) :
    RecyclerView.Adapter<AdapterDetailsEpisodes.ViewHolder>() {

    private var mListCharacters = listOf<HeroEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHeroBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mListCharacters[position])

        onHolderEventListener.onHideProgressBar()
    }

    override fun getItemCount() = mListCharacters.size

    fun setData(list: List<HeroEntity>) {
        //val diff = DiffUtilsCallbackEpisodesDetails(mListEpisodes, list)
        //val diffResult = DiffUtil.calculateDiff(diff)
        mListCharacters = list
        //diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()

    }

    interface OnClickEventDetailsEpisodesRv {
        fun onClickHero(heroEntity: HeroEntity)
    }

    interface OnHolderEventListener {
        fun onHideProgressBar()
    }

    inner class ViewHolder(private val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                mOnClickEventDetailsHeroes.onClickHero(
                    mListCharacters[adapterPosition]
                )
            }
        }

        fun bind(heroEntity: HeroEntity) {
            binding.nameChapter.text = heroEntity.name
            Glide.with(context)
                .load(heroEntity.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarHero.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarHero.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageChapter)
            binding.speciesChapter.text = heroEntity.species
            binding.statusChapter.text = heroEntity.status
            binding.genderChapter.text = heroEntity.gender
        }
    }
}