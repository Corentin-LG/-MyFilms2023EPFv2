package fr.epf.mm.myfilms2023v3.model

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