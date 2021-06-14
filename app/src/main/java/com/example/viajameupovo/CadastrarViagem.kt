package com.example.viajameupovo

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.viajameupovo.db.DatabaseHandler
import com.example.viajameupovo.model.Viagem
import kotlinx.android.synthetic.main.activity_cadastrar_viagem.*
import java.text.SimpleDateFormat
import java.util.*

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

        //Configurar Calendar
        val textView: TextView = findViewById(R.id.etData)
        textView.text = SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis())

        var cal = java.util.Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(java.util.Calendar.YEAR, year)
            cal.set(java.util.Calendar.MONTH, monthOfYear)
            cal.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)
        }

        textView.setOnClickListener {
            DatePickerDialog(this@CadastrarViagem, dateSetListener,
                cal.get(java.util.Calendar.YEAR),
                cal.get(java.util.Calendar.MONTH),
                cal.get(java.util.Calendar.DAY_OF_MONTH)).show()
        }

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

                    //Verifica se está no modo de edição
                    if (intent.getStringExtra("mode") == "Edit") {
                        viagem = populateViagem(viagem!!)
                        databaseHandler.updateViagem(viagem!!)
                    } else {
                        viagem = populateViagem(null)
                        databaseHandler.addViagem(viagem!!) }
                    finish()

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