package fr.epf.mm.myfilms2023v3.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
public interface FilmDao {
    @Insert
    fun insert(film: Film)
    @Update
    fun update(film: Film)
    @Delete
    fun delete(film: Film)
    @Query("SELECT * FROM films Where title = :title")
    fun findFilmByTitle(title: String): Film
    @Query("SELECT * FROM films Where title Like :title")
    fun findFilmsByTitle(title: String): List<Film>
    @Query("SELECT * FROM films Where id = :id")
    fun findFilmsById(id: Long): Long
    @Query("SELECT * FROM films")
    fun findAllFilms(): List<Film>
    @RawQuery
    fun getFilmViaQuery(query: SupportSQLiteQuery): Film
}