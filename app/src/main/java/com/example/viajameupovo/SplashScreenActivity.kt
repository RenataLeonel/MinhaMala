package com.example.viajameupovo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /*// validação opcional com SharedPreferences para abertura ocasional da spashscreen
        val verificaAbeturaSplash: SharedPreferences =
            getSharedPreferences("VERIFICAR_SPLASH", Context.MODE_PRIVATE)*/
        exibeSplash()
    }

    // método para chamar a Intent mainActivity
    fun exibeMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // método com handler para delay e exibição da splash e posterior carregamento da MainActivity
    fun exibeSplash() {
        var DURACAO: Long = 4000
        Handler().postDelayed(Runnable {
            exibeMainActivity()
        }, DURACAO)
    }
}