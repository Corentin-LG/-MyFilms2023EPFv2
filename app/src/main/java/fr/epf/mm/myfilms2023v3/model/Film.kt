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
    val overview : String,

) : Parcelable

//@Entity(tableName = "films")
//@Parcelize
//data class Film (
//    @PrimaryKey val id : Long,
//    val title : String,
//    val poster : String,
//    val release : String
//
//) : Parcelable