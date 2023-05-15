package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.myfilms2023v3.model.Film
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//view_film
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
                synchro2()
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun synchro() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://randomuser.me/")
            .build()

        val service = retrofit.create(PopularFilmService::class.java)

        runBlocking {
            val films = service.getFilms().results.map {
                Log.d("EPF", "$it")
//                Film(
//                    it.name.last,
//                    it.name.first,
//                    if (it.gender == "male") Gender.MAN else Gender.WOMAN
//                )
            }
//            recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
        }
    }
}