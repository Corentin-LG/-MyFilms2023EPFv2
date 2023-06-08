package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val averageTextView = findViewById<TextView>(R.id.details_film_vote_average_textview)
        val languageTextView = findViewById<TextView>(R.id.details_film_original_language_textview)

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
//            if (checkLongType(scannedUrl)) {
//                titleTextView.text = film?.title ?: "Non renseigné"
//                releaseTextView.text = film?.release ?: "Non renseigné"
//                overviewTextView.text = film?.overview ?: "Non renseigné"
//
//                film?.let { Glide.with(imageView).load(IMAGE_BASE + it.poster).into(imageView) }
//            } else {

            titleTextView.text = ""
            releaseTextView.text = ""
            overviewTextView.text = "Voici le résultat de la recherche :"
            averageTextView.text = ""
            languageTextView.text = ""
            imageView.setImageDrawable(
                resources.getDrawable(
                    R.drawable.baseline_search_24,
                    null
                )
            )
//            }
        } else if (film != null) {
            titleTextView.text = film?.title ?: "Non renseigné"
            releaseTextView.text = film?.release ?: "Non renseigné"
            overviewTextView.text = film?.overview ?: "Non renseigné"
            val voteAverage = film?.vote_average
            if (voteAverage != null) {
                val formattedVoteAverage = "$voteAverage/10"
                averageTextView.text = formattedVoteAverage
            } else {
                averageTextView.text = "Non renseigné"
            }
            val originalLanguage = film?.original_language

            if (originalLanguage != null) {
                val formattedLanguage = "Langage : ${originalLanguage}."
                languageTextView.text = formattedLanguage
            } else {
                languageTextView.text = "Non renseigné"
            }
//            averageTextView.text = film?.vote_average.toString() ?: "Non renseigné"
//            languageTextView.text = film?.original_language ?: "Non renseigné"

            film?.let { Glide.with(imageView).load(IMAGE_BASE + it.poster).into(imageView) }

            searchByUser()
        } else {
            Log.d("Erreurscan", "au secours")
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView_details)
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
                searchByUser()
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

        val myList = mutableListOf<Pair<Long, String>>()
        val longNumber: Long
        longNumber = 28
        val element = Pair(longNumber, "action")
        myList.add(element)

        runBlocking {
            try {
                val films =
                    service.getFilms("/3/discover/movie?api_key=$apiKey&with_genres=${film?.genreID1}").results.map {
                        Film(
                            it.id,
                            it.title,
                            it.poster_path ?: "",
                            it.release_date,
                            it.overview ?: "",
                            (it.vote_average ?: "") as Float,
                            it.original_language ?: "",
                            if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 28
                            //if(it.genre_ids.isNotEmpty()) it.genre_ids else myList.map { it.first }
                            //if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else 28
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

        val urlQuery: String

        val myList = mutableListOf<Pair<Long, String>>()
        val longNumber: Long
        longNumber = 28
        val element = Pair(longNumber, "action")
        myList.add(element)

        if (checkLongType(searchQuery)) {
            urlQuery = "/3/movie/$searchQuery?api_key=$apiKey"
            Log.d("queryQRcode", "1:$urlQuery")

            runBlocking {
                try {
                    val filmResultGetByID = service.getFilm(urlQuery)
                    Log.d("queryQRcode", "1.1:$filmResultGetByID")
                    val filmGetByID = Film(
                        filmResultGetByID.id,
                        filmResultGetByID.title,
                        filmResultGetByID.poster_path ?: "",
                        filmResultGetByID.release_date,
                        filmResultGetByID.overview ?: "",
                        (filmResultGetByID.vote_average ?: "") as Float,
                        filmResultGetByID.original_language ?: "",
                        if (filmResultGetByID.genres.isNotEmpty()) filmResultGetByID.genres[0].id else myList[0].first
//                        if (filmResultGetByID.genres.id.isNotEmpty()) filmResultGetByID.genres.id[0] else myList[0].first
//                        if (filmResultGetByID.genres.id.isNotEmpty()) filmResultGetByID.genres.id else myList.map { it.first }
                    )
                    Log.d("queryQRcode", "1.2:$filmGetByID")
                    val film = mutableListOf<Film>()
                    film.add(filmGetByID)
                    Log.d("queryQRcode", "2:$film")
                    recyclerView.adapter = FilmAdapter(this@DetailsFilmActivity, film)
                } catch (e: Exception) {
                    Log.d("ExceptionFilm", e.message!!)
                    Log.d("queryQRcode", e.message!!)
                }
            }
        } else {
            urlQuery = "/3/search/movie?api_key=$apiKey&query=$searchQuery"
            runBlocking {
                try {
                    val films =
                        service.getFilms(urlQuery).results.map {
                            Film(
                                it.id,
                                it.title,
                                it.poster_path ?: "",
                                it.release_date,
                                it.overview ?: "",
                                (it.vote_average ?: "") as Float,
                                it.original_language ?: "",
                                if (it.genre_ids.isNotEmpty()) it.genre_ids[0] else myList[0].first
                                //if(it.genre_ids.isNotEmpty()) it.genre_ids else myList.map { it.first }
                            )
                        }
                    recyclerView.adapter = FilmAdapter(this@DetailsFilmActivity, films)
                } catch (e: Exception) {
                    Log.d("ExceptionFilm", e.message!!)
                }
            }
        }
        Log.d("queryQRcode", "$urlQuery")
    }

    override fun onResume() {
        super.onResume()
        InternetConnectivityChecker.checkInternetConnectivity(this)
    }

}

fun checkLongType(variable: Any): Boolean {
    return try {
        variable.toString().toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}