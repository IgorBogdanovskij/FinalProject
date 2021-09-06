package com.example.rickyandmorty_finalproject_anderson.data.locale.models.hero

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.Location
import com.example.rickyandmorty_finalproject_anderson.data.remote.models.hero.Origin
import kotlinx.parcelize.Parcelize

@Entity(tableName = HeroEntity.TABLE_NAME)
@Parcelize
data class HeroEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    @Embedded
    val location: Location,
    @Embedded
    val origin: Origin,
    val type: String,
    val url: String,
    val created: String,
    val episode: List<String>
):Parcelable {
    companion object {
        const val TABLE_NAME = "hero_table"
    }

}



