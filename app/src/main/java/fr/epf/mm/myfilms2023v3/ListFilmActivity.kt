package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.epf.mm.myfilms2023v3.model.AppDatabase
import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import android.view.View

class ListFilmActivity : AppCompatActivity() {

    val apiKey = "003dbf4d555d5ab3a9f692a799bf78bb"
    private var searchQuery: String = ""

    lateinit var appDatabase: FilmsDatabase

    lateinit var recyclerView: RecyclerView
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_film)

        recyclerView = findViewById(R.id.list_film_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val queryTextListener = MyQueryTextListener(this@ListFilmActivity)
        searchView = findViewById(R.id.search_bar_searchview)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(queryTextListener)

        appDatabase = AppDatabase.getInstance(this)
        synchro()

        bottomNavigationView = findViewById(R.id.bottomNavigationView_list)
        bottomNavigationView.setSelectedItemId(R.id.home_view)
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
        menuInflater.inflate(R.menu.synchro_films, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_synchro_film -> {
                synchro()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun synchro() {
        val retrofitFilm = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org")
            .build()

        val service = retrofitFilm.create(FilmService::class.java)

        runBlocking {
            try {
                val films = service.getFilms("/3/movie/popular?api_key=$apiKey").results.map {
                    Film(
                        it.id,
                        it.title,
                        it.poster_path ?: "",
                        it.release_date,
                        it.overview ?: "",
                        if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 28
//                        if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 0

//                        (it.genre_ids.get(0)?:"") as Int,
//                        (it.genre_ids.get(1)?:"") as Int,
//                        (it.genre_ids.get(2)?:"") as Int

                        //si la classe change, il faut suppr l'appli soit trouver comment suppr a data base de la version actuelle
                        //la migration n'a pas marché
                    )
                }
                recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
            } catch (e: Exception) {
                Log.d("ExceptionFilm", e.message!!)
            }
        }
    }

    fun searchByUser(query: String) {
        searchQuery = query

        val retrofitFilm = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org")
            .build()

        val service = retrofitFilm.create(FilmService::class.java)

        val myList = mutableListOf<Pair<Long, String>>()
        val longNumber:Long
        longNumber = 28
        val element = Pair(longNumber, "action")
        myList.add(element)

        runBlocking {
            try {
                val films =
                    service.getFilms("/3/search/movie?api_key=$apiKey&query=$searchQuery").results.map {
                        Film(
                            it.id,
                            it.title,
                            it.poster_path ?: "",
                            it.release_date,
                            it.overview ?: "",
                            if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else myList[0].first
                            //(if(it.genre_ids.isNotEmpty()) it.genre_ids.get(0) else myList.map { it.[0].first }) as Long
                            //if(it.genre_ids.isNotEmpty()) it.genre_ids else myList.map { it.first }
                        )
                    }
                recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
            } catch (e: Exception) {
                Log.d("ExceptionFilm", e.message!!)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        InternetConnectivityChecker.checkInternetConnectivity(this)
    }
}

fun View.click(action: (View) -> Unit) {
    this.setOnClickListener(action)
}

fun View.addFav(action: (View) -> Unit) {
    this.setOnClickListener(action)
}