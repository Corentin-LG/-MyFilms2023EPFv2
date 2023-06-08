package fr.epf.mm.myfilms2023v3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "films")
@Parcelize
data class Film(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val release: String,
    val overview: String,
    val vote_average: Float,
    val original_language: String,
    val genreID1: Long

) : Parcelable


//val genreID1: List<Long>

//@Entity(tableName = "films")
//@Parcelize
//data class Film (
//    @PrimaryKey val id : Long,
//    val title : String,
//    val poster : String,
//    val release : String,
//    val overview : String,
//    val genreID1 : Int,
//    val genreID2 : Int,
//    val genreID3 : Int
//
//) : Parcelable