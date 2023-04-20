package fr.epf.mm.myfilms2023v3.model

//https://api.themoviedb.org/3/movie/550?api_key=003dbf4d555d5ab3a9f692a799bf78bb
//003dbf4d555d5ab3a9f692a799bf78bb

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film (
    val lastName: String,
    val firstName: String,
    val gender: Gender
) : Parcelable {

}