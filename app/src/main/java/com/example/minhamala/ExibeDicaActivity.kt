package com.example.minhamala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.minhamala.adapter.ExibeDicaAdapter
import kotlinx.android.synthetic.main.activity_exibe_dica.*


// falta implementar os nomes dos títulos dos vídeos e setar seus respectivos thumbnails
class ExibeDicaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibe_dica)
        val movieTitle = arrayOf("Bonito (MT)", "Girl", "World", "Rabbit 2", "Girl 2", "World 2")
        val description = arrayOf(
            "A fat rabbit.",
            "Staring at the sun.",
            "Earth has been spinning on its axis faster lately—the fastest ever recorded.",
            "Another gig in the woods",
            "part 2, continuing...",
            "Earth spins and never stops and this is great!"
        )
        val thumbs = arrayOf(
            R.drawable.card_bonito,
            R.drawable.card_cabo,
            R.drawable.card_foz,
            R.drawable.card_gramado,
            R.drawable.card_ilha,
            R.drawable.card_jeri,
            R.drawable.card_maceio,
            R.drawable.card_maragogi,
            R.drawable.card_porto,
            R.drawable.card_salvador,
            R.drawable.card_vitoria
        )
        val adapter = ExibeDicaAdapter(this, movieTitle, description, thumbs)
        listMovies.adapter = adapter

        listMovies.setOnItemClickListener { adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            val option = adapter.getItem(position)
            val intent = Intent(this, PlayerDicasActivity::class.java)
            intent.putExtra("option", option.toString())
            startActivity(intent)
        }
    }
}