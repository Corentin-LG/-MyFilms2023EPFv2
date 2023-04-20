package fr.epf.mm.myfilms2023v3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener

private const val TAG = "AddFilmActivity"
private const val AGE_MAX = 65
private const val AGE_MIN = 18

class AddFilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_film)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        val lastNameEditext = findViewById<EditText>(R.id.add_film_last_name_edit_text)
        val addButton = findViewById<Button>(R.id.add_film_button)

        val genderRadioGroup = findViewById<RadioGroup>(R.id.add_film_gender_radiogroup)
        genderRadioGroup.check(R.id.add_film_gender_man_radiobutton)

        val ageTextview = findViewById<TextView>(R.id.add_film_age_textview)
        val ageSeekbar = findViewById<SeekBar>(R.id.add_film_age_seekbar)
        ageSeekbar.max = AGE_MAX - AGE_MIN
        ageSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                ageTextview.text = (progress+ AGE_MIN).toString()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })

        val levelSpinner = findViewById<Spinner>(R.id.add_film_level_spinner)

        addButton.setOnClickListener{

            val message = getString(R.string.add_client_message_succes, lastNameEditext.text)
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
            Log.d("EPF", "Nom : ${lastNameEditext.text}")

            val gender =
                if(genderRadioGroup.checkedRadioButtonId == R.id.add_film_gender_man_radiobutton) 1 else 2
            Log.d(TAG, "genre: ${gender}")
            Log.d(TAG, "Niveau : ${levelSpinner.selectedItem}")

            finish()

        }

    }
}