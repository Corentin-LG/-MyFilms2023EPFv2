package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListFilmActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_film)

        recyclerView = findViewById<RecyclerView>(R.id.list_film_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

        val appDatabase = Room.databaseBuilder(
            applicationContext,
            FilmsDatabase::class.java, "filmDatabase"
        ).build()

        runBlocking {
            val films = service.getFilms().results.map {
                Log.d("EPF", "$it")
                Film(
                    it.id,
                    it.title,
                    it.poster_path,
                    it.release_date
                )
            }
            recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
        }
    }

    private fun synchro2() {
        val retrofitFilm = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org")
            .build()

        val service = retrofitFilm.create(PopularFilmService::class.java)

        runBlocking {
            val films = service.getFilms().results.map {
                Log.d("EPF", "$it")
                Film(
                    it.id,
                    it.title,
                    it.poster_path,
                    it.release_date
                )
            }
            recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
        }
    }
}