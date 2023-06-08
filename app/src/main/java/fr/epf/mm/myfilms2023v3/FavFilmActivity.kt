package fr.epf.mm.myfilms2023v3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_film)

        appDatabase = AppDatabase.getInstance(this)

        recyclerView = findViewById(R.id.list_film_recyclerview_fav_film)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_local_db, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh_db -> {
                this.recreate()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}