package fr.epf.mm.myfilms2023v3

import androidx.appcompat.widget.SearchView
import android.text.Editable
import android.text.TextWatcher

class MyQueryTextListener : SearchView.OnQueryTextListener, TextWatcher {
    override fun onQueryTextSubmit(query: String): Boolean {
        performSearch(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        performSearch(newText)
        return true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Ne rien faire
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Ne rien faire
    }

    override fun afterTextChanged(s: Editable?) {
        val query = s.toString()
        performSearch(query)
    }

    private fun performSearch(query: String) {
        println("Texte de recherche : $query")
        // Effectuer l'action souhaitée avec le texte de recherche
    }
}
