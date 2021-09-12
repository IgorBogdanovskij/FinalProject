package com.example.rickyandmorty_finalproject_anderson.presentation.heroes.ui.recycler

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

class AdapterHeroes(
    val context: Context,
    private val callback: OnLoadNextPage,
    private val onClickListener: OnCLickHolder,
    private val onHolderEventListener: OnHolderEventListener
) : RecyclerView.Adapter<AdapterHeroes.ViewHolder>() {

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
        mListCharacters = list
        notifyDataSetChanged()
    }

    interface OnLoadNextPage {
        fun onLoad()
    }

    interface OnHolderEventListener {
        fun onHideProgressBar()
    }

    interface OnCLickHolder {
        fun onClick(hero: HeroEntity)
    }

    inner class ViewHolder(private val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClickListener.onClick(mListCharacters[adapterPosition]) }
        }

        fun bind(Hero: HeroEntity) {

            if ((adapterPosition >= itemCount - 1 && itemCount >= 20 && itemCount < 672))
                callback.onLoad()

            binding.nameChapter.text = Hero.name
            Glide.with(context)
                .load(Hero.image)
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
            binding.speciesChapter.text = Hero.species
            binding.statusChapter.text = Hero.status
            binding.genderChapter.text = Hero.gender
        }
    }
}