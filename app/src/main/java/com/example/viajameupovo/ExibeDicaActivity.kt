package com.example.viajameupovo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.viajameupovo.adapter.ExibeDicaAdapter
import kotlinx.android.synthetic.main.activity_exibe_dica.*

class ExibeDicaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibe_dica)

        // variável do tipo array para armazenamento de títulos (string)
        val card_title = arrayOf("Bonito (MT)", "Porto Seguro (BA)", "Gramado (RS)", "São Paulo (SP)", "Jericoacoara (CE)", "Maceió (AL)", "Maragogi (AL)", "Espírito Santo (ES)")

        // variável do tipo array para armazenamento de descrições (string)
        val card_desc = arrayOf(
            "Todo o mistério do Pantanal",
            "O suingue da Bahia",
            "Pérolas do sul do Brasil",
            "O belo litoral paulista",
            "Jeri é o paraiso?",
            "Dicas imperdíveis de Maceió",
            "Maragogi e seus encantos",
            "Curiosidades da terra Capixaba"
        )
        // variável do tipo array para armazenamento de imagens (resources)
        val thumbs = arrayOf(
            R.drawable.card_bonito,
            R.drawable.card_porto,
            R.drawable.card_gramado,
            R.drawable.card_ilha,
            R.drawable.card_jeri,
            R.drawable.card_maceio,
            R.drawable.card_maragogi,
            R.drawable.card_vitoria
        )
        // instancia adapter customizado passando parâmetros solicitados
        val adapter = ExibeDicaAdapter(this, thumbs, card_title, card_desc)
        listDicas.adapter = adapter

        listDicas.setOnItemClickListener { adapterView: AdapterView<*>, view: View, position: Int, id: Long ->
            // variável recebe a posição do item na lista
            val option = adapter.getItem(position)
            // variável do tipo Intent é associada com página de destno (PlayerDicasActivity)
            val intent = Intent(this, PlayerDicasActivity::class.java)
            intent.putExtra("option", option.toString())
            startActivity(intent)
        }
    }
}