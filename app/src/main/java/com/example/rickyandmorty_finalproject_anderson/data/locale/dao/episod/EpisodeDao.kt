package com.example.rickyandmorty_finalproject_anderson.data.locale.dao.episod

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.episod.EpisodeEntity
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episodes_table ORDER BY id")
    fun getAllEpisodes(): Single<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes_table WHERE id = :id")
    fun getSingleEpisode(id:Int): Single<EpisodeEntity>

    @Query("SELECT * FROM episodes_table WHERE name LIKE '%' || :name || '%' ")
    fun getEpisodesBySearch(name: String):Single<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes_table WHERE episode = :episode")
    fun getSingleEpisode(episode:String): Single<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(list: List<EpisodeEntity>):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneEpisode(episodeEntity: EpisodeEntity):Completable
}
