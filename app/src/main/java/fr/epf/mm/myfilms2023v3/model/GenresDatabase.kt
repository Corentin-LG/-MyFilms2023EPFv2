package fr.epf.mm.myfilms2023v3.model

import androidx.room.RoomDatabase;
import androidx.room.Database

@Database(entities = [Genres::class], version = 1)
abstract class GenresDatabase : RoomDatabase() {
    abstract fun genresDao(): GenresDao
}