package fr.epf.mm.myfilms2023v3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.myfilms2023v3.model.Film
import com.bumptech.glide.Glide
import fr.epf.mm.myfilms2023v3.model.AppDatabase
import fr.epf.mm.myfilms2023v3.model.FilmsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view)

class FilmAdapter(val context: Context, val films: List<Film>) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_film, parent, false)
        Log.d("FilmAdapt", films.toString())
        return FilmViewHolder(view)
    }

    override fun getItemCount() = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

        val film = films[position]
        val view = holder.itemView
        val textView = view.findViewById<TextView>(R.id.view_film_textview)
        Log.d("FilmAdapt", film.title)
        textView.text = "${film.id} ${film.title}"
        //textView.text = "${film.id} ${film.title} ${film.release}"

        val imageView = view.findViewById<ImageView>(R.id.view_film_imageview)

        Glide.with(imageView).load(IMAGE_BASE + film.poster).into(imageView)

        val cardView = view.findViewById<CardView>(R.id.view_film_cardview)

        val favouriteButton = view.findViewById<ImageView>(R.id.favourite_film_imageview)

        favouriteButton.setImageResource(R.drawable.baseline_favorite_border_24)


        cardView.click {
            val intent = Intent(context, DetailsFilmActivity::class.java)
            intent.putExtra("film", film)
            context.startActivity(intent)
        }

        favouriteButton.addFav {
            var appDatabase: FilmsDatabase
            appDatabase = AppDatabase.getInstance(context)
            val favFilmObject = films[position]

            var filmCherche: Film?
            runBlocking {
                withContext(Dispatchers.IO) {
                    filmCherche = appDatabase.filmDao().findFilmsById(favFilmObject.id)
                }
            }
            if (filmCherche == null) {
                // le film est à ajouter
                runBlocking {
                    withContext(Dispatchers.IO) {
                        appDatabase.filmDao().insert(films[position])
                    }
                }
            } else {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        appDatabase.filmDao().delete(films[position])
                    }
                }
            }



        }

    }

}