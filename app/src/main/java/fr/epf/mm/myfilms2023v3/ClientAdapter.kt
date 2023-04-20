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
import fr.epf.mm.myfilms2023v3.model.Client
import fr.epf.mm.myfilms2023v3.model.Gender

class ClientViewHolder(view: View) : RecyclerView.ViewHolder(view)


class ClientAdapter(val context: Context, val clients: List<Client>) : RecyclerView.Adapter<ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount() = clients.size

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        val view = holder.itemView
        val textView = view.findViewById<TextView>(R.id.view_client_textview)
        textView.text = "${client.firstName} ${client.lastName}"

        val imageView = view.findViewById<ImageView>(R.id.view_client_imageview)

        imageView.setImageResource(client.getImage())

        val cardView = view.findViewById<CardView>(R.id.view_client_cardview)

        cardView.click {
            val intent = Intent(context, DetailsClientActivity::class.java)
            intent.putExtra("client", client)
            context.startActivity(intent)
        }

    }

}



