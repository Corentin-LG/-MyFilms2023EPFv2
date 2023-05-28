package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import fr.epf.mm.myfilms2023v3.model.Film

private const val PICTURE_REQUEST_CODE = 100

class DetailsFilmActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_film)

        val lastnameTextView = findViewById<TextView>(R.id.details_film_lastname_textview)
        val film = intent.extras?.get("film") as? Film

        val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

        lastnameTextView.text = film?.title ?: "Non renseigné"
        imageView = findViewById<ImageView>(R.id.details_film_imageview)
        //Glide.with(imageView).load(IMAGE_BASE + film.poster).into(imageView)
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
}