package com.example.rickyandmorty_finalproject_anderson.data.locale.dao.hero

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero.HeroEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id")
    fun getAllHeroes():Single<List<HeroEntity>>

    @Query("SELECT * FROM hero_table WHERE id = :id")
    fun getSingleHero(id:Int):Single<HeroEntity>

    @Query("SELECT * FROM hero_table ORDER BY id")
    fun getAllHeroesWithoutLiveData():List<HeroEntity>

    @Query("SELECT * FROM hero_table WHERE id IN (:arr)")
    fun getAllHeroesByFilters(arr:List<Int>):List<HeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeroes(list: List<HeroEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneHero(heroEntity: HeroEntity): Completable

    @Query("SELECT * FROM hero_table WHERE name LIKE '%' || :name || '%'")
    fun getHeroesBySearch(name: String):Single<List<HeroEntity>>
}