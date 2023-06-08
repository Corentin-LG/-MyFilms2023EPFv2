package fr.epf.mm.myfilms2023v3

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FilmService {
    @GET
    suspend fun getFilm(@Url url: String): GetFilmsResult
}

data class GetFilmsResult(@Query("page") val page: Int, @Query("results") val results: List<Movie>)
data class Movie(
    @Query("adult") val adult: Boolean,
    @Query("backdrop_path") val backdrop_path: String,
    @Query("genre_ids") val genre_ids: List<Int>,
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

//https://api.themoviedb.org/3/discover/movie?api_key=003dbf4d555d5ab3a9f692a799bf78bb&sort_by=popularity.desc&with_genres=28&page=1
//https://api.themoviedb.org/3/discover/movie?api_key=003dbf4d555d5ab3a9f692a799bf78bb&sort_by=popularity.desc&with_genres=28&page=2