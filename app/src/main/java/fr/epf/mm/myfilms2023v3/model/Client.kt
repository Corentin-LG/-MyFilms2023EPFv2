package fr.epf.mm.myfilms2023v3.model

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize


enum class Gender {
    MAN, WOMAN
}

// https://developer.android.com/kotlin/parcelize?hl=fr#groovy
@Parcelize
data class Client (
    val lastName: String,
    val firstName: String,
    val gender: Gender
) : Parcelable {

}

