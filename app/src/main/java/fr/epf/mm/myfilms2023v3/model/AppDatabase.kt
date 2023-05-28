package fr.epf.mm.myfilms2023v3.model

import android.content.Context
import androidx.room.Room

object AppDatabase {
    private var instance: FilmsDatabase? = null

    fun getInstance(context: Context): FilmsDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                FilmsDatabase::class.java, "filmDatabase"
            ).build().also { instance = it }
        }
    }
}
