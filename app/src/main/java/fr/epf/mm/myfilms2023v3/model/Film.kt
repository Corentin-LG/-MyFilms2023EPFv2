package fr.epf.mm.myfilms2023v3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "films")
@Parcelize
data class Film (
    @PrimaryKey val id : Long,
    val title : String,
    val poster : String,
    val release : String,
    val overview : String,
    val genreID1 : Int

) : Parcelable

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