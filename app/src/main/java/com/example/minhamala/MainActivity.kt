package com.example.minhamala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Aos clicar em minhas viagens a página é redirecionada para a activity ViagemActivity
        btnMinhasViagens.setOnClickListener{
            val intent = Intent(this, ViagemActivity::class.java)
            startActivity(intent)
        }

        //Aos clicar em calculadora a página é redirecionada para a activity CalculadoraActivity
        btnCalculadora.setOnClickListener{
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }
    }
}