package fr.epf.mm.myfilms2023v3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.epf.mm.myfilms2023v3.model.AppDatabase
import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListFilmActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var appDatabase: FilmsDatabase
    lateinit var searchView: androidx.appcompat.widget.SearchView
    val apiKey ="003dbf4d555d5ab3a9f692a799bf78bb"

    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_film)

        recyclerView = findViewById(R.id.list_film_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        appDatabase = AppDatabase.getInstance(this)

        searchView = findViewById(R.id.search_bar_searchview)
        searchView.clearFocus()
        //val queryTextListener = MyQueryTextListener(this)
        val queryTextListener = MyQueryTextListener(this@ListFilmActivity)
        searchView.setOnQueryTextListener(queryTextListener)

        synchro()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_films, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_film -> {
                val intent = Intent(this, AddFilmActivity::class.java)
                startActivity(intent)
            }
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

        val service = retrofitFilm.create(PopularFilmService::class.java)

        runBlocking {
            try {
                //val films = service.getFilms().results.map {
                ///3/search/movie?query=star&include_adult=false&language=en-US&page=1"
                ///3/search/movie?query=star"
                val films = service.getFilm("/3/movie/popular?api_key=$apiKey").results.map {
                    Log.d("EPF", "$it")
                    Film(
                        it.id,
                        it.title,
                        it.poster_path?:"",
                        it.overview?:""
                    //si la classe change, il faut suppr l'appli soit trouver comment suppr a data base de la version atctuelle
                    //la migration n'a pas marché
                    )
                }
//                withContext(Dispatchers.IO) {
//                    appDatabase.filmDao().insertAll(films)
//                    Log.d("ExceptionFilm", appDatabase.filmDao().findAllFilms().toString())
//                }
                //Log.d("ExceptionFilm", appDatabase.toString())
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

        val service = retrofitFilm.create(PopularFilmService::class.java)

        runBlocking {
            try {
                //https://api.themoviedb.org/3/search/movie?api_key=003dbf4d555d5ab3a9f692a799bf78bb&query=star
                val films = service.getFilm("/3/search/movie?api_key=$apiKey&query=$searchQuery").results.map {
                    Log.d("EPF", "$it")
                    Film(
                        it.id,
                        it.title,
                        it.poster_path?:"",
                        it.overview?:""
                    )
                }
//                withContext(Dispatchers.IO) {
//                    appDatabase.filmDao().insertAll(films)
//                }
                recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
            } catch (e: Exception) {
                Log.d("ExceptionFilm", e.message!!)
            }
        }
    }

}

fun View.click(action : (View) -> Unit){
    Log.d("CLICK", "click")
    this.setOnClickListener(action)
}