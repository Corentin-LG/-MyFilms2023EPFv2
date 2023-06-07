package fr.epf.mm.myfilms2023v3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.myfilms2023v3.model.AppDatabase
import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FavFilmActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var appDatabase: FilmsDatabase
    lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_film)

        recyclerView = findViewById(R.id.list_film_recyclerview_fav_film)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        appDatabase = AppDatabase.getInstance(this)

        searchView = findViewById(R.id.search_bar_searchview_fav_film)
        searchView.clearFocus()
        //val queryTextListener = MyQueryTextListener(this)
        val queryTextListener = MyQueryTextListener(this@FavFilmActivity)
        searchView.setOnQueryTextListener(queryTextListener)

        var favFilmsToDisplay = listOf<Film>()
        runBlocking {
            withContext(Dispatchers.IO) {
                favFilmsToDisplay = appDatabase.filmDao().findAllFilms()
            }
        }
        recyclerView.adapter =
            FilmAdapter(this@FavFilmActivity, favFilmsToDisplay)
    }
}