package com.example.minhamala

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // simples armazenagem do status da SplashScreen para uso com SharedPreferences
        val verificaAbeturaSplash: SharedPreferences =
            getSharedPreferences("VERIFICAR_SPLASH", Context.MODE_PRIVATE)
        exibeSplash()
    }

    // método para chamada mainActivity
    fun exibeMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // método para controle de exibição da SplashScreen e carregamento da MainActivity
    fun exibeSplash() {
        var DURACAO: Long = 4000
        Handler().postDelayed(Runnable {
            exibeMainActivity()
        }, DURACAO)
    }
}