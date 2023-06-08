package fr.epf.mm.myfilms2023v3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_film)

        val queryTextListener = MyQueryTextListener(this@FavFilmActivity)

        appDatabase = AppDatabase.getInstance(this)

        recyclerView = findViewById(R.id.list_film_recyclerview_fav_film)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchView = findViewById(R.id.search_bar_searchview_fav_film)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(queryTextListener)

        var favFilmsToDisplay = listOf<Film>()
        runBlocking {
            withContext(Dispatchers.IO) {
                favFilmsToDisplay = appDatabase.filmDao().findAllFilms()
            }
        }

        recyclerView.adapter =
            FilmAdapter(this@FavFilmActivity, favFilmsToDisplay)

        bottomNavigationView = findViewById(R.id.bottomNavigationView_fav)
        bottomNavigationView.setSelectedItemId(R.id.favourites_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.qrcode_view -> {
                    startActivity(Intent(this, QRCodeScannerActivity::class.java))
                    true
                }
                R.id.home_view -> {
                    startActivity(Intent(this, ListFilmActivity::class.java))
                    true
                }
                R.id.favourites_view -> {
                    startActivity(Intent(this, FavFilmActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}