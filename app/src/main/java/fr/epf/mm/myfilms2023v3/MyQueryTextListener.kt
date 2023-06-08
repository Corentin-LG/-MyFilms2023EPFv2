package fr.epf.mm.myfilms2023v3

import android.app.Activity
import androidx.appcompat.widget.SearchView
import android.text.Editable
import android.text.TextWatcher

class MyQueryTextListener(private val activity: Activity) : SearchView.OnQueryTextListener,
    TextWatcher {
    override fun onQueryTextSubmit(query: String): Boolean {
        (activity as ListFilmActivity).searchByUser(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        // Vous pouvez gérer les changements de texte en cours de saisie ici
        return true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Ne rien faire
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Ne rien faire
    }

    override fun afterTextChanged(s: Editable?) {
        // Ne rien faire
    }
}

