package fr.epf.mm.myfilms2023v3

import fr.epf.mm.myfilms2023v3.model.Film
import retrofit2.http.GET

interface PopularFilmService {

    @GET("/3/movie/popular?api_key=003dbf4d555d5ab3a9f692a799bf78bb")
    suspend fun getFilms() : GetPopularFilmsResult
}

data class GetPopularFilmsResult(val page: Int, val results: List<Movie>)
data class Movie(val adult: Boolean?,
                 val backdrop_path: String?,
                 val id: Long,
                 val original_language: String?,
                 val original_title: String?,
                 val overview: String?,
                 val popularity: Float?,
                 val poster_path: String,
                 val release_date: String,
                 val title: String,
                 val video: Boolean?,
                 val vote_average: Float?,
                 val vote_count: Int?)