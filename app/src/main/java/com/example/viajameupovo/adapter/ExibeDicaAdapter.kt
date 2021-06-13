package com.example.viajameupovo.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.viajameupovo.R

class ExibeDicaAdapter(
    private val context: Activity,
    private val image: Array<Int>,
    private val card_title: Array<String>,
    private val card_desc: Array<String>
) : ArrayAdapter<String>(
    context,
    R.layout.lista_exibe_dica,
    card_title
) {
    // método do adapter para preenchimento do ListView com layoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflate = context.layoutInflater
        val row = inflate.inflate(R.layout.lista_exibe_dica, null, true)
        val title = row.findViewById(R.id.card_title) as TextView
        val description = row.findViewById(R.id.card_description) as TextView
        val thumbId = row.findViewById(R.id.thumb) as ImageView

        title.text = card_title[position]
        description.text = card_desc[position]
        thumbId.setImageResource(image[position])

        return row
    }
}

