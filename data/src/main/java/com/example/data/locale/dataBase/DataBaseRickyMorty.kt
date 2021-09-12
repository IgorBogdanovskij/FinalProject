package com.example.rickyandmorty_finalproject_anderson.data.locale.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.locale.dao.episod.EpisodeDao
import com.example.data.locale.dao.hero.HeroDao
import com.example.data.locale.dao.location.LocationDao
import com.example.domain.models.episod.EpisodeEntity
import com.example.domain.models.hero.HeroEntity
import com.example.domain.models.location.LocationEntity

@Database(
    entities = [HeroEntity::class, EpisodeEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class DataBaseRickyMorty : RoomDatabase() {

    abstract fun heroDao(): HeroDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao

    companion object {
        const val NAME_DB = "RickyMorty_database"
    }
}