package com.example.viajameupovo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.viajameupovo.db.DatabaseHandler
import com.example.viajameupovo.model.Viagem
import kotlinx.android.synthetic.main.activity_cadastrar_viagem.*

class CadastrarViagem : AppCompatActivity() {

    val databaseHandler = DatabaseHandler(this)
    var viagem: Viagem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_viagem)

        //Configuração do spinner
        val arraySpinner = listOf<String>(getString(R.string.nao), getString(R.string.sim))
        val arraySpinnerAdapter =
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraySpinner)
        spnCheckin.adapter = arraySpinnerAdapter

        //Se estiver em modo de edição carrega os dados da viagem por id
        if (intent.getStringExtra("mode") == "Edit") {
            viagem = databaseHandler.getViagem(intent.getIntExtra("id", 0))
            tvTitle.text = getString(R.string.editViagem) //Titulo da página
            etLocal.setText(viagem!!.local)
            etData.setText(viagem!!.data)
            spnCheckin.setSelection(arraySpinnerAdapter.getPosition(viagem!!.checkin))
            btnDel.setOnClickListener{
                databaseHandler.delViagem(viagem!!.id)
                finish()
            }
        //Se não o botão delete fica invisível
        } else{ btnDel.visibility = View.GONE }

        //Botão salvar
        btnSave.setOnClickListener{
            //Verifica validação do local
            if (validaLocal()) {
                //Verifica validação da data
                if (validaData()) {
                    //Verifica se está no modo de edição
                    if (intent.getStringExtra("mode") == "Edit") {
                        viagem = populateViagem(viagem!!)
                        databaseHandler.updateViagem(viagem!!)
                    } else {
                        viagem = populateViagem(null)
                        databaseHandler.addViagem(viagem!!) }
                    finish()

                } else { Toast.makeText(this, R.string.erroData, Toast.LENGTH_SHORT).show() }

            } else { Toast.makeText(this, R.string.erroLocal, Toast.LENGTH_SHORT).show() }
        }

        //Botão cancelar
        btnCancel.setOnClickListener{
            finish()
        }
    }

    //Validar campo vazio em local
    //Retorna true se atender aos requisitos
    fun validaLocal(): Boolean{
        return etLocal.text.toString() != ""
    }

    //Validar campo vazio e com mais de 10 caracteres na data
    //Retorna true se atender aos requisitos
    fun validaData(): Boolean{
        return etData.text.toString() != "" && etLocal.text.toString().length == 10
    }

    //Popular lista de viagem
    private fun populateViagem(p0: Viagem?): Viagem{
        val viagem = Viagem()
        if(p0 != null) viagem.id = p0.id
        viagem.local = etLocal.text.toString()
        viagem.data = etData.text.toString()
        viagem.checkin = spnCheckin.selectedItem.toString()
        return viagem //Retorna lista populada
    }
}