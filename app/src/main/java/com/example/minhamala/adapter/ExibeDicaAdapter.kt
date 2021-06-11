package com.example.minhamala.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minhamala.R

class ExibeDicaAdapter(
    private val context: Activity,
    private val card_titl: Array<String>,
    private val descript: Array<String>,
    private val image: Array<Int>
) : ArrayAdapter<String>(
    context,
    R.layout.lista_exibe_dica,
    card_titl
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflate = context.layoutInflater
        val row = inflate.inflate(R.layout.lista_exibe_dica, null, true)
        val title = row.findViewById(R.id.card_title) as TextView
        val description = row.findViewById(R.id.description) as TextView
        val thumbId = row.findViewById(R.id.thumb) as ImageView
        title.text = card_titl[position]
        description.text = descript[position]
        thumbId.setImageResource(image[position])
        return row

    }
}

