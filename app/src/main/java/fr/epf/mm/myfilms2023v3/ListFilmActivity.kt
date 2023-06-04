package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.epf.mm.myfilms2023v3.model.AppDatabase
import fr.epf.mm.myfilms2023v3.model.Film
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListFilmActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_film)

        recyclerView = findViewById(R.id.list_film_recyclerview)
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

//        val appDatabase = Room.databaseBuilder(
//            applicationContext,
//            FilmsDatabase::class.java, "filmDatabase"
//        ).build()

        val appDatabase = AppDatabase.getInstance(this)

        runBlocking {
            try {
                val films = service.getFilms().results.map {
                    Log.d("EPF", "$it")
                    Film(
                        it.id,
                        it.title,
                        it.poster_path,
                        it.release_date
                    )
                }
                Log.d("ExceptionFilm", "test1")
                withContext(Dispatchers.IO) {
                    appDatabase.filmDao().insertAll(films)
                    Log.d("ExceptionFilm", appDatabase.filmDao().findAllFilms().toString())
                }
                Log.d("ExceptionFilm", "test3")
                Log.d("ExceptionFilm", appDatabase.toString())
                Log.d("ExceptionFilm", "test4")
                recyclerView.adapter = FilmAdapter(this@ListFilmActivity, films)
                Log.d("ExceptionFilm", "test5")
            } catch (e: Exception) {
                Log.d("ExceptionFilm", "test6")
                Log.d("ExceptionFilm", e.message!!)
            }
            Log.d("ExceptionFilm", "test7")
        }
    }
}