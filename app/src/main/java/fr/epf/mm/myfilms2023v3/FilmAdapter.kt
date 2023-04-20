package fr.epf.mm.myfilms2023v3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.myfilms2023v3.model.Film

class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view)


class FilmAdapter(val context: Context, val films: List<Film>) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun getItemCount() = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        val view = holder.itemView
        val textView = view.findViewById<TextView>(R.id.view_film_textview)
        textView.text = "${film.firstName} ${film.lastName}"

        val imageView = view.findViewById<ImageView>(R.id.view_film_imageview)

        imageView.setImageResource(film.getImage())

        val cardView = view.findViewById<CardView>(R.id.view_film_cardview)

        cardView.click {
            val intent = Intent(context, DetailsClientActivity::class.java)
            intent.putExtra("film", film)
            context.startActivity(intent)
        }

    }

}