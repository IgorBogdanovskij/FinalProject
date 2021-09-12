package com.example.rickyandmorty_finalproject_anderson.di.data

import android.content.Context
import androidx.room.Room
import com.example.data.locale.dao.episod.EpisodeDao
import com.example.data.locale.dao.hero.HeroDao
import com.example.data.locale.dao.location.LocationDao
import com.example.data.remote.api.episodes.ApiQueryEpisodes
import com.example.data.remote.api.episodes.ApiQueryEpisodesImp
import com.example.data.remote.api.heroes.ApiQueryHeroes
import com.example.data.remote.api.heroes.ApiQueryHeroesImp
import com.example.data.remote.api.locations.ApiQueryLocations
import com.example.data.remote.api.locations.ApiQueryLocationsImp
import com.example.data.repository.RepositoryEpisodesImp
import com.example.data.repository.RepositoryHeroesImp
import com.example.data.repository.RepositoryLocationsImp
import com.example.domain.repository.IRepositoryEpisodes
import com.example.domain.repository.IRepositoryHeroes
import com.example.domain.repository.IRepositoryLocations
import com.example.rickyandmorty_finalproject_anderson.data.locale.dataBase.DataBaseRickyMorty
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDataBaseRickyMorty(
        context: Context
    ): DataBaseRickyMorty = Room.databaseBuilder(
        context, DataBaseRickyMorty::class.java,
        DataBaseRickyMorty.NAME_DB
    )
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideEpisodeDao(
        dataBaseRickyMorty: DataBaseRickyMorty
    ): EpisodeDao {
        return dataBaseRickyMorty.episodeDao()
    }

    @Singleton
    @Provides
    fun provideHeroDao(
        dataBaseRickyMorty: DataBaseRickyMorty
    ): HeroDao {
        return dataBaseRickyMorty.heroDao()
    }

    @Singleton
    @Provides
    fun provideLocationsDao(dataBaseRickyMorty: DataBaseRickyMorty): LocationDao {
        return dataBaseRickyMorty.locationDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeApi(
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): ApiQueryEpisodes {
        return ApiQueryEpisodesImp(gsonConverterFactory, rxJava3CallAdapterFactory)
    }

    @Singleton
    @Provides
    fun provideHeroApi(
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): ApiQueryHeroes {
        return ApiQueryHeroesImp(gsonConverterFactory, rxJava3CallAdapterFactory)
    }

    @Singleton
    @Provides
    fun provideLocationApi(
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): ApiQueryLocations {
        return ApiQueryLocationsImp(gsonConverterFactory, rxJava3CallAdapterFactory)
    }

    @Singleton
    @Provides
    fun provideEpisodeRepository(
        heroDao: HeroDao,
        episodeDao: EpisodeDao,
        apiQueryHeroes: ApiQueryHeroes,
        apiQueryEpisodes: ApiQueryEpisodes,
    ): IRepositoryEpisodes {
        return RepositoryEpisodesImp(
            heroDao,
            episodeDao,
            apiQueryHeroes,
            apiQueryEpisodes
        )
    }

    @Singleton
    @Provides
    fun provideHeroRepository(
        heroDao: HeroDao,
        episodeDao: EpisodeDao,
        locationDao: LocationDao,
        apiQueryHeroes: ApiQueryHeroes,
        apiQueryEpisodes: ApiQueryEpisodes,
        apiQueryLocations: ApiQueryLocations,
    ): IRepositoryHeroes {
        return RepositoryHeroesImp(
            apiQueryHeroes,
            apiQueryEpisodes,
            apiQueryLocations,
            heroDao,
            episodeDao,
            locationDao
        )
    }


    @Singleton
    @Provides
    fun provideLocationRepository(
        heroDao: HeroDao,
        locationDao: LocationDao,
        apiQueryHeroes: ApiQueryHeroes,
        apiQueryLocations: ApiQueryLocations
    ): IRepositoryLocations {
        return RepositoryLocationsImp(
            apiQueryHeroes,
            apiQueryLocations,
            heroDao,
            locationDao
        )
    }
}