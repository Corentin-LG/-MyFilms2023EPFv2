package fr.epf.mm.myfilms2023v3.model

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
public interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: Film)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(films: List<Film>)
    @Update
    fun update(film: Film)
    @Delete
    fun delete(film: Film)
    @Query("SELECT * FROM films Where title = :title")
    fun findFilmByTitle(title: String): Film
    @Query("SELECT * FROM films Where title Like :title")
    fun findFilmsByTitle(title: String): List<Film>
    @Query("SELECT * FROM films Where id = :id")
    fun findFilmsById(id: Long): Film
    @Query("SELECT * FROM films")
    fun findAllFilms(): List<Film>
    @RawQuery
    fun getFilmViaQuery(query: SupportSQLiteQuery): Film
}