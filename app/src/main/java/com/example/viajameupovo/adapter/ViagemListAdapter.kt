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

class ViagemListAdapter(viagemList: ArrayList<Viagem>, internal var ctx:Context, private val callback: (Int) -> Unit):
    RecyclerView.Adapter<ViagemListAdapter.ViewHolder>(){

    private var viagemList = ArrayList<Viagem>()

    init{
        this.viagemList = viagemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.lista_exibe_viagem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viagem = viagemList[position]
        holder.local.text = viagem.local
        holder.data.text = viagem.data
        holder.checkin.text = viagem.checkin

        if(viagem.checkin == ctx.getString(R.string.sim)) holder.checkin.setTextColor(ctx.getColor(R.color.green))
        else holder.checkin.setTextColor(ctx.getColor(R.color.red))

        holder.lay.setOnClickListener{
            callback(viagem.id)
        }
        if(position == viagemList.size-1) holder.endLine.visibility = View.VISIBLE //Torna a endLine visivel na ultima posição
    }

    override fun getItemCount(): Int {
        return viagemList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var lay: LinearLayout = view.layContentViagem
        var local: TextView =  view.tvContentLocal
        var data: TextView = view.tvContentData
        var checkin: TextView = view.tvContentCheckin
        var endLine: LinearLayout = view.endLine
    }
}