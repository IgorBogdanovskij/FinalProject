package com.example.rickyandmorty.data.locale.dao.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickyandmorty.domain.models.location.LocationEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface LocationDao {

    @Query("SELECT * FROM location_table ORDER BY id")
    fun getAllLocations(): Single<List<LocationEntity>>

    @Query("SELECT * FROM location_table WHERE name LIKE '%' || :name || '%' ")
    fun getLocationsBySearch(name: String): Single<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(list: List<LocationEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneLocation(locationEntity: LocationEntity): Completable

    @Query("SELECT * FROM location_table WHERE (type LIKE :type AND dimension LIKE :dimension) OR (type LIKE :type OR dimension LIKE :dimension)")
    fun getLocationsByFilters(type: String, dimension: String): Single<List<LocationEntity>>
}