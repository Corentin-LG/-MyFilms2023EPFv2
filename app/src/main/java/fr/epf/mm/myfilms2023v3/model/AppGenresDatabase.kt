package fr.epf.mm.myfilms2023v3.model

import android.content.Context
import androidx.room.Room

object AppGenresDatabase {
    private var instance: GenresDatabase? = null

    fun getInstance(context: Context): GenresDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                GenresDatabase::class.java, "genresDatabase"
            ).build().also { instance = it }
        }
    }
}
