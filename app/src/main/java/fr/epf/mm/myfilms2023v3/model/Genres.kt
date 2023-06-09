package fr.epf.mm.myfilms2023v3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "genres")
@Parcelize
data class Genres (
    @PrimaryKey val id: Long,
    val name: String

) : Parcelable