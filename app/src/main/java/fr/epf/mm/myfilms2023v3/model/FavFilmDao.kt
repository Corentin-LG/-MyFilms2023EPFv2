package fr.epf.mm.myfilms2023v3.model

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
public interface FavFilmDao {
    @Insert
    fun insert(favfilms: FavFilm)
    @Update
    fun update(favfilms: FavFilm)
    @Delete
    fun delete(favfilms: FavFilm)
    @Query("DELETE FROM favfilms")
    fun nukeTable()
    @Query("SELECT * FROM favfilms Where title = :title")
    fun findFilmByTitle(title: String): FavFilm
    @Query("SELECT * FROM favfilms Where title Like :title")
    fun findFilmsByTitle(title: String): List<FavFilm>
    @Query("SELECT * FROM favfilms Where id = :id")
    fun findFilmsById(id: Long): Long
    @Query("SELECT * FROM favfilms")
    fun findAllFilms(): List<FavFilm>
    @RawQuery
    fun getFilmViaQuery(query: SupportSQLiteQuery): FavFilm
}