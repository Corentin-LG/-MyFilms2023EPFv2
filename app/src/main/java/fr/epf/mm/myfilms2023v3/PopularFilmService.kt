package fr.epf.mm.myfilms2023v3

import fr.epf.mm.myfilms2023v3.model.Film
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularFilmService {

    @GET("/3/movie/popular?api_key=003dbf4d555d5ab3a9f692a799bf78bb")
    suspend fun getFilms() : GetPopularFilmsResult
}

data class GetPopularFilmsResult(@Query("page") val page: Int,@Query("results") val results: List<Movie>)
data class Movie(@Query("adult") val adult: Boolean,
                 @Query("backdrop_path") val backdrop_path: String,
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
                 @Query("vote_count") val vote_count: Int?)


//https://api.themoviedb.org/3/discover/movie?api_key=003dbf4d555d5ab3a9f692a799bf78bb&sort_by=popularity.desc&with_genres=28&page=1
//https://api.themoviedb.org/3/discover/movie?api_key=003dbf4d555d5ab3a9f692a799bf78bb&sort_by=popularity.desc&with_genres=28&page=2

//interface PopularFilmService {
//
//    @GET("/3/movie/popular?api_key=003dbf4d555d5ab3a9f692a799bf78bb")
//    suspend fun getFilms() : GetPopularFilmsResult
//}
//
//data class GetPopularFilmsResult(val page: Int, val results: List<Movie>)
//data class Movie(val adult: Boolean?,
//                 val backdrop_path: String?,
//                 val id: Long,
//                 val original_language: String?,
//                 val original_title: String?,
//                 val overview: String?,
//                 val popularity: Float?,
//                 val poster_path: String,
//                 val release_date: String,
//                 val title: String,
//                 val video: Boolean?,
//                 val vote_average: Float?,
//                 val vote_count: Int?)