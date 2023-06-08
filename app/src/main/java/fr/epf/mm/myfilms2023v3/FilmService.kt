package fr.epf.mm.myfilms2023v3

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FilmService {
    @GET
    suspend fun getFilms(@Url url: String): GetFilmsResult
    @GET
    suspend fun getFilm(@Url url: String): SingleMovie
}

data class GetFilmsResult(@Query("page") val page: Int, @Query("results") val results: List<Movie>)
//data class GetFilmResult(@Query("result") val result: SingleMovie)
data class Movie(
    @Query("adult") val adult: Boolean,
    @Query("backdrop_path") val backdrop_path: String,
    @Query("genre_ids") val genre_ids: List<Long>,
    @Query("id") val id: Long,
    @Query("original_language") val original_language: String,
    @Query("original_title") val original_title: String,
    @Query("overview") val overview: String,
    @Query("popularity") val popularity: Float,
    @Query("poster_path") val poster_path: String,
    @Query("release_date") val release_date: String,
    @Query("title") val title: String,
    @Query("video") val video: Boolean,
    @Query("vote_average") val vote_average: Float,
    @Query("vote_count") val vote_count: Int?
)

data class SingleMovie(
    @Query("adult") val adult: Boolean,
    @Query("backdrop_path") val backdrop_path: String,
//    @Query("genres") val genres: GenresAndID,
    @Query("genres") val genres: List<GenresAndID>,
    @Query("id") val id: Long,
    @Query("original_language") val original_language: String,
    @Query("original_title") val original_title: String,
    @Query("overview") val overview: String,
    @Query("popularity") val popularity: Float,
    @Query("poster_path") val poster_path: String,
    @Query("release_date") val release_date: String,
    @Query("title") val title: String,
    @Query("video") val video: Boolean,
    @Query("vote_average") val vote_average: Float,
    @Query("vote_count") val vote_count: Int?
)

data class GenresAndID(
//    @Query("id") val id: List<Long>,
//    @Query("name") val name: List<String>
    @Query("id") val id: Long,
    @Query("name") val name: String
)