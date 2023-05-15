package fr.epf.mm.myfilms2023v3.model

//https://api.themoviedb.org/3/movie/550?api_key=003dbf4d555d5ab3a9f692a799bf78bb
//003dbf4d555d5ab3a9f692a799bf78bb

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    val id : Long,
    val title : String,
    val poster : String,
    val release : String

) : Parcelable{

}

//data class Film (
//    @SerializedName("id")
//    val id : String ?,
//
//    @SerializedName("title")
//    val title : String?,
//
//    @SerializedName("poster_path")
//    val poster : String?,
//
//    @SerializedName("release_date")
//    val release : String?
//
//) : Parcelable{
//    constructor() : this("", "", "", "")
//}