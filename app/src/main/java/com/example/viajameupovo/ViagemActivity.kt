package com.example.viajameupovo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viajameupovo.adapter.ViagemListAdapter
import com.example.viajameupovo.db.DatabaseHandler
import com.example.viajameupovo.model.Viagem
import kotlinx.android.synthetic.main.activity_viagem.*

class ViagemActivity : AppCompatActivity() {

    var viagemList = ArrayList<Viagem>()
    var viagemListAdapter: ViagemListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viagem)

        criarViagem.setOnClickListener{
            val intent = Intent(this, CadastrarViagem::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        initView()
    }

    private fun initView(){
        viagemList = databaseHandler.getViagemList()
        viagemListAdapter = ViagemListAdapter(viagemList, this, this::editViagem)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = viagemListAdapter
    }

    private fun editViagem(id: Int){
        val intent = Intent(this, CadastrarViagem::class.java)
        intent.putExtra("mode", "Edit")
        intent.putExtra("id", id)
        startActivity(intent)
    }
}