package fr.epf.mm.myfilms2023v3

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.epf.mm.myfilms2023v3.model.Film
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val PICTURE_REQUEST_CODE = 100

class DetailsFilmActivity : AppCompatActivity() {

    val apiKey = "003dbf4d555d5ab3a9f692a799bf78bb"

    lateinit var imageView: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_film)

        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

        val titleTextView = findViewById<TextView>(R.id.details_film_title_textview)
        val releaseTextView = findViewById<TextView>(R.id.details_film_release_textview)
        val overviewTextView = findViewById<TextView>(R.id.details_film_overview_textview)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        imageView = findViewById<ImageView>(R.id.details_film_imageview)
        recyclerView = findViewById(R.id.list_similary_film_recyclerview)
        recyclerView.layoutManager = layoutManager


        val scannedUrl = intent?.getStringExtra("scannedData")
        Log.d("Erreurscan", "$scannedUrl")
        val film = intent.extras?.get("film") as? Film
        Log.d("Erreurscan", "$film")

        if (scannedUrl != null) {
            searchByUser(scannedUrl)
        } else if (film != null) {
            titleTextView.text = film?.title ?: "Non renseigné"
            releaseTextView.text = film?.release ?: "Non renseigné"
            overviewTextView.text = film?.overview ?: "Non renseigné"

            film?.let { Glide.with(imageView).load(IMAGE_BASE + it.poster).into(imageView) }

            searchByUser()
        } else {
            Log.d("Erreurscan", "au secours")
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView_details)
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
        menuInflater.inflate(R.menu.details_film, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_picture_film -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, PICTURE_REQUEST_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        when (requestCode) {
            PICTURE_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = result?.getParcelableExtra<Bitmap>("data")
                    imageView.setImageBitmap(bitmap)
                    Toast.makeText(this, "Photo mise à jour", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, result)
    }

    fun searchByUser() {
        val film = intent.extras?.get("film") as? Film

        val retrofitFilm = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org")
            .build()

        val service = retrofitFilm.create(FilmService::class.java)

        runBlocking {
            try {
                val films =
                    service.getFilm("/3/discover/movie?api_key=$apiKey&with_genres=${film?.genreID1}").results.map {
                        Film(
                            it.id,
                            it.title,
                            it.poster_path ?: "",
                            it.release_date,
                            it.overview ?: "",
                            if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 28
                        )
                    }
                Log.d("Genres", "${film?.genreID1}")
                Log.d("Genres", "$films")
                recyclerView.adapter = FilmAdapter(this@DetailsFilmActivity, films)
            } catch (e: Exception) {
                Log.d("ExceptionFilm", e.message!!)
            }
        }
    }

    fun searchByUser(query: String) {
        val searchQuery = query

        val retrofitFilm = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org")
            .build()

        val service = retrofitFilm.create(FilmService::class.java)

        runBlocking {
            try {
                val films =
                    service.getFilm("/3/search/movie?api_key=$apiKey&query=$searchQuery").results.map {
                        Film(
                            it.id,
                            it.title,
                            it.poster_path ?: "",
                            it.release_date,
                            it.overview ?: "",
                            if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 0
                        )
                    }
                recyclerView.adapter = FilmAdapter(this@DetailsFilmActivity, films)
            } catch (e: Exception) {
                Log.d("ExceptionFilm", e.message!!)
            }
        }
    }
}