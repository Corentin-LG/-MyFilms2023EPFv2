package fr.epf.mm.myfilms2023v3.model

import androidx.room.RoomDatabase;
import androidx.room.Database

@Database(entities = [FavFilm::class], version = 1)
abstract class FavFilmsDatabase : RoomDatabase() {
    abstract fun favfilmDao(): FavFilmDao
}