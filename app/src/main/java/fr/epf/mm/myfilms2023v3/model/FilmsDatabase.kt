package fr.epf.mm.myfilms2023v3.model

import androidx.room.RoomDatabase;
import androidx.room.Database

@Database(entities = [Film::class], version = 1)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}