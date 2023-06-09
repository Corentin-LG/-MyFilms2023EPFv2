package fr.epf.mm.myfilms2023v3.model
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
public interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: Genres)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genress: List<Genres>)

    @Update
    fun update(genres: Genres)

    @Delete
    fun delete(genres: Genres)

    @Query("SELECT * FROM genres Where name = :name")
    fun findGenresByName(name: String): Genres

    @Query("SELECT * FROM genres Where name Like :name")
    fun findGenressByName(name: String): List<Genres>

    @Query("SELECT * FROM genres Where id = :id")
    fun findGenresById(id: Long): Genres

    @Query("SELECT * FROM genres")
    fun findAllGenress(): List<Genres>

    @RawQuery
    fun getFilmViaQuery(query: SupportSQLiteQuery): Genres
}