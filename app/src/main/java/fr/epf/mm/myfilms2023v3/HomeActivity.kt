package fr.epf.mm.myfilms2023v3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val addButton = findViewById<Button>(R.id.home_add_button)
        val listButton = findViewById<Button>(R.id.home_list_button)
        val addFilmButton = findViewById<Button>(R.id.home_add_film_button)
        val listFilmButton = findViewById<Button>(R.id.home_list_film_button)

        addButton.click {
            val intent = Intent(this, AddClientActivity::class.java)
            startActivity(intent)
        }

        listButton.click {
                val intent = Intent(this, ListClientActivity::class.java)
                startActivity(intent)
        }

        addFilmButton.click {
            val intent = Intent(this, AddFilmActivity::class.java)
            startActivity(intent)
        }

        listFilmButton.click {
            val intent = Intent(this, ListFilmActivity::class.java)
            startActivity(intent)
        }

    }
}

fun View.click(action : (View) -> Unit){
    Log.d("CLICK", "click")
    this.setOnClickListener(action)
}