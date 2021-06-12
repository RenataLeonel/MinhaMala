package com.example.minhamala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.minhamala.model.CalculadoraModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_calculadora.*

//Para uma requisição funcionar é necessário:
// importar o Volley e o gson (não esquecer de sincronizar - sync)
// configurar permissão de acesso a internet no AndroidManifest.xml
// criar uma classe com os dados que vc quer apresentar ao usuário
class CalculadoraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        /* Na biblioteca Volley, toda a comunicação web e até o tratamento de cache é
        através de requisições. E o principal mecanismo para isso, é uma fila
        de requisições, a RequestQueue.*/
        val queue = Volley.newRequestQueue(this)

        //Requisição para a cotação do EURO
        iconeEuro.setOnClickListener{
            var url = "https://economia.awesomeapi.com.br/EUR-BRL"
            // Resposta de string da requisição
            val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val gson = GsonBuilder().create()

                    val result =
                        gson.fromJson(response.toString(), Array<CalculadoraModel>::class.java).toList()

                    var nome = result.firstOrNull()?.name.toString()
                    var valor = result.firstOrNull()?.high.toString()
                    Toast.makeText(this, nome + ": € " + valor, Toast.LENGTH_LONG).show()
                },
                //Tratamento de erro
                Response.ErrorListener { tvCotacaoCalculadora.text = "ERROR " })
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }

        //Requisição para a cotação do DÓLAR
        iconeDolar.setOnClickListener{
            var url = "https://economia.awesomeapi.com.br/USD-BRL"
            // Resposta de string da requisição
            val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val gson = GsonBuilder().create()

                    val result =
                        gson.fromJson(response.toString(), Array<CalculadoraModel>::class.java).toList()

                    var nome = result.firstOrNull()?.name.toString()
                    var valor = result.firstOrNull()?.high.toString()
                    Toast.makeText(this, nome + ": U$ " + valor, Toast.LENGTH_LONG).show()
                },
                //Tratamento de erro
                Response.ErrorListener { tvCotacaoCalculadora.text = "ERROR " })
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }

}