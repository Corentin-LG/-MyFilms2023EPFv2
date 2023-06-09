package fr.epf.mm.myfilms2023v3

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GenresService {
    @GET
    suspend fun getGenress(@Url url: String): GetGenressResult
}
data class GetGenressResult(@Query("genres") val genres: List<GenresAndID2>)

data class GenresAndID2(
    @Query("id") val id: Long,
    @Query("name") val name: String
)


//interface GenresService {
//    @GET
//    suspend fun getGenress(@Url url: String): List<GenresAndID2>
//}
//
//data class GenresAndID2(
//    @Query("id") val id: Long,
//    @Query("name") val name: String
//)