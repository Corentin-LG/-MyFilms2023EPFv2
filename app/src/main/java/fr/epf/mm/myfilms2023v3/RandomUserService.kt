package fr.epf.mm.myfilms2023v3

import fr.epf.mm.myfilms2023v3.model.Client
import retrofit2.http.GET

interface RandomUserService {

    @GET("api/?results=20")
    suspend fun getUsers() : GetUsersResult
}

data class GetUsersResult(val results: List<User>)
data class User(val gender: String, val name: Name)
data class Name(val first: String, val last: String)

