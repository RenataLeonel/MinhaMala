package com.example.viajameupovo

import android.content.Intent
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*

class PlayerDicasActivity : AppCompatActivity() {
    private var mediaControler: MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        // variável que recebe o parâmetro da intent para seleção de vídeos
        val optionName = intent.getStringExtra("option")

        // bloco recursivo para seleção do vídeo a ser exibido
        if (optionName.toString() == "Bonito (MT)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/video-bonito.mp4")
        } else if (optionName.toString() == "Porto Seguro (BA)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/porto-seguro.mp4")
        } else if (optionName.toString() == "Gramado (RS)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/gramado.mp4")
        } else if (optionName.toString() == "São Paulo (SP)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/ilha-bela.mp4")
        } else if (optionName.toString() == "Jericoacoara (CE)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/jericoacoara.mp4")
        } else if (optionName.toString() == "Maceió (AL)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/maceio.mp4")
        } else if (optionName.toString() == "Maragogi (AL)") {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/maragogi.mp4")
        } else {
            executeVideoFile("https://fabio.bsb.br/wp-content/uploads/2021/06/vila-velha.mp4")
        }

        // variável para envio de recomendação de conteúdo por texto pela intent implícita de compartilhamento
        val mensagem:String = "Conheci o destino turístico de ${optionName} no app Viaja Meu Povo e você também deveria ver."

        // FAB (floating action button) para compartilhamento do vídeo
        compartilharVideo.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mensagem)
                type = "text/plain"
            }
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            }
        }
    }

    // função para executar o player de vídeo, usando URL fornecida e instância do mediaControler
    fun executeVideoFile(url: String) {
        playerView.setVideoPath(url)
        mediaControler = MediaController(this)
        mediaControler?.setAnchorView(playerView)
        playerView.setMediaController(mediaControler)
        playerView.start()
    }
}