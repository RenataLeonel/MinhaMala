package com.example.minhamala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.minhamala.db.DatabaseHandler
import com.example.minhamala.model.Viagem
import kotlinx.android.synthetic.main.activity_cadastrar_viagem.*

class CadastrarViagem : AppCompatActivity() {

    val databaseHandler = DatabaseHandler(this)
    var viagem: Viagem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_viagem)

        val arraySpinner = listOf<String>(getString(R.string.nao), getString(R.string.sim))
        val arraySpinnerAdapter =
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner)
        spnCheckin.adapter = arraySpinnerAdapter

        //Se estiver em modo de edição carrega os dados da viagem por id
        if (intent.getStringExtra("mode") == "Edit") {
            viagem = databaseHandler.getViagem(intent.getIntExtra("id", 0))
            tvTitle.text = getString(R.string.editViagem)
            etLocal.setText(viagem!!.local)
            etData.setText(viagem!!.data)
            spnCheckin.setSelection(arraySpinnerAdapter.getPosition(viagem!!.checkin))
            btnDel.setOnClickListener{
                databaseHandler.delViagem(viagem!!.id)
                finish()
            }
        }
        else{
            btnDel.visibility = View.GONE
        }
        btnSave.setOnClickListener{
            if(intent.getStringExtra("mode") == "Edit"){
                viagem = populateViagem(viagem)
                databaseHandler.updateViagem(viagem!!)
            }
            else{
                viagem = populateViagem(null)
                databaseHandler.addViagem(viagem!!)
            }
            finish()
        }
        btnCancel.setOnClickListener{
            finish()
        }
    }

    //Popular lista de viagem
    private fun populateViagem(p0: Viagem?): Viagem{
        val viagem = Viagem()
        if(p0 != null) viagem.id = p0.id
        viagem.local = etLocal.text.toString()
        viagem.data = etData.text.toString()
        viagem.checkin = spnCheckin.selectedItem.toString()
        return viagem
    }
}