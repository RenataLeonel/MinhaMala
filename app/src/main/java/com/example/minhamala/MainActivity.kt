package com.example.minhamala

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // verifica se splashscreen já foi iniciada alguma vez
        val verificaAbeturaSplash: SharedPreferences =
            getSharedPreferences("VERIFICAR_SPLASH", Context.MODE_PRIVATE)




        btnMinhasViagens.setOnClickListener{
            val intent = Intent(this, ViagemActivity::class.java)
            startActivity(intent)
        }
    }
}