package fr.epf.mm.myfilms2023v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.myfilms2023v3.model.Client
import fr.epf.mm.myfilms2023v3.model.Gender
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListClientActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_client)

        recyclerView = findViewById<RecyclerView>(R.id.list_client_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_clients, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_client -> {
                val intent = Intent(this, AddClientActivity::class.java)
                startActivity(intent)
            }
            R.id.action_synchro -> {
                synchro()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun synchro() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://randomuser.me/")
            .build()

        val service = retrofit.create(RandomUserService::class.java)

        runBlocking {
            val clients = service.getUsers().results.map {
                Log.d("EPF", "$it")
                Client(
                    it.name.last,
                    it.name.first,
                    if (it.gender == "male") Gender.MAN else Gender.WOMAN
                )
            }
            recyclerView.adapter = ClientAdapter(this@ListClientActivity, clients)
        }
    }
}