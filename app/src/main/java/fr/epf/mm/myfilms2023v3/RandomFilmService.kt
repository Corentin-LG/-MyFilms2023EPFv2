package fr.epf.mm.myfilms2023v3

import fr.epf.mm.myfilms2023v3.model.Film
import retrofit2.http.GET

interface RandomFilmService {

    @GET("/3/movie/popular?api_key=003dbf4d555d5ab3a9f692a799bf78bb")
    suspend fun getFilms() : GetFilmsResult
}

data class GetFilmsResult(val results: List<Film>)

