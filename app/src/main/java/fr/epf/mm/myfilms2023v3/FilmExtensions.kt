package fr.epf.mm.myfilms2023v3

import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.Gender

fun Film.getImage() = when (this.gender) {
    Gender.MAN -> R.drawable.man
    Gender.WOMAN -> R.drawable.woman
}
