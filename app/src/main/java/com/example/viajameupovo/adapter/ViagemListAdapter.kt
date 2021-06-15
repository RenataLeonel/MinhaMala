package com.example.viajameupovo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viajameupovo.R
import com.example.viajameupovo.model.Viagem
import kotlinx.android.synthetic.main.lista_exibe_viagem.view.*

//Adapter da recyclerView
//Funciona como um contêiner para receber o conjunto de dados
class ViagemListAdapter(viagemList: ArrayList<Viagem>, internal var ctx:Context, private val callback: (Int) -> Unit):
    RecyclerView.Adapter<ViagemListAdapter.ViewHolder>(){

    private var viagemList = ArrayList<Viagem>()

    init{ //Inicializa a lista
        this.viagemList = viagemList
    }

    //Cria um viewHolder e infla com o layout informado, ou seja,  cria e inicializa o ViewHolder e o View associado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.lista_exibe_viagem, parent, false)
        return ViewHolder(view)
    }

    // associa um ViewHolder aos dados
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viagem = viagemList[position]
        holder.local.text = viagem.local
        holder.data.text = viagem.data
        holder.checkin.text = viagem.checkin

        //Se a opção do checkin for sim recebe a cor verde
        if(viagem.checkin == ctx.getString(R.string.sim)) holder.checkin.setTextColor(ctx.getColor(R.color.green))
        //Se não recebe a cor vermelha
        else holder.checkin.setTextColor(ctx.getColor(R.color.red))

        //retorna a viagem que iremos chamar na ViagemActivity
        holder.lay.setOnClickListener{
            callback(viagem.id)
        }
        //Torna a endLine visivel na ultima posição da lista do RecyclerView
        if(position == viagemList.size-1) holder.endLine.visibility = View.VISIBLE
    }

    //Verifica o tamanho da lista
    //Esse método é usado no RecyclerView para determinar quando não hpa mais itens a serem exibidos
    override fun getItemCount(): Int {
        return viagemList.size
    }

    //variáveis que chamam as views utilizadas nos métodos acima
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var lay: LinearLayout = view.layContentViagem
        var local: TextView =  view.tvContentLocal
        var data: TextView = view.tvContentData
        var checkin: TextView = view.tvContentCheckin
        var endLine: LinearLayout = view.endLine
    }
}