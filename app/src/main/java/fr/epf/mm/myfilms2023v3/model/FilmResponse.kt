package fr.epf.mm.myfilms2023v3.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmResponse(
    @SerializedName("results")
    val films : List<Film>

) : Parcelable {
    constructor() : this(mutableListOf())
}