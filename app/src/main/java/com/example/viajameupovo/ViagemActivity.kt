package com.example.viajameupovo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viajameupovo.adapter.ViagemListAdapter
import com.example.viajameupovo.db.DatabaseHandler
import com.example.viajameupovo.model.Viagem
import kotlinx.android.synthetic.main.activity_viagem.*

//Para usar RecyclerView é necessário incluir a dependência: implementation 'androidx.recyclerview:recyclerview:1.1.0'
//Criar uma classe model
//Obs.: Configuração do BD no DatabaseHandler
class ViagemActivity : AppCompatActivity() {

    var viagemList = ArrayList<Viagem>()
    var viagemListAdapter: ViagemListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var databaseHandler = DatabaseHandler(this)

    //Notificação
    lateinit var notificationManager: NotificationManager //Notifica o usuário
    lateinit var notificationChannel: NotificationChannel //Configurar canais (Permite agrupar diferentes notificações)
    lateinit var builder: Notification.Builder //Configurar a notificação

    private val channelID = "notification" //Id para o canal
    private val desc = "Notifications" //Descrição
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viagem)

        //Ao clicar em no botão "+" chama a activity CadastrarViagem
        criarViagem.setOnClickListener{
            val intent = Intent(this, CadastrarViagem::class.java)
            startActivity(intent)
        }

    }

    override fun onResume(){
        super.onResume()
        initView()
    }

    //Inicializa a lista que deve ser apresentada ao abrir essa activity
    private fun initView(){

        //Busca informações da lista
        viagemList = databaseHandler.getViagemList()
        viagemListAdapter = ViagemListAdapter(viagemList, this, this::editViagem)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = viagemListAdapter

        //Se a viagemList estiver vazia chama o método exibeNotification
        if (viagemList.isEmpty()){
            exibeNotification()
        }

    }

    //Abre a activity com a opção editar viagem
    private fun editViagem(id: Int){
        val intent = Intent(this, CadastrarViagem::class.java)
        intent.putExtra("mode", "Edit")
        intent.putExtra("id", id)
        startActivity(intent)
    }

    //Exibe notificação
    private fun exibeNotification(){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Inicializar channel passando Id, descrição e nível de importância para a notificationManager
        notificationChannel = NotificationChannel(channelID,desc,NotificationManager.IMPORTANCE_HIGH)

        //Configurando notification
        notificationChannel.lightColor = Color.GRAY
        notificationChannel.enableVibration(true)

        //Inicializar manager criando o canal da notificação channel
        notificationManager.createNotificationChannel(notificationChannel)

        //Contruindo o corpo da notificação com builder
        builder  = Notification.Builder(this)
            .setContentTitle("Não há viagens cadastradas:") //setar título
            .setContentText("Clique no botão para cadastrar sua viagem!")//setar texto
            .setChannelId(channelID) //setar id
            .setSmallIcon(R.drawable.ic_add) //setar ícone

        //Mostrar notification para o usuário passando id e o corpo da notificação
        notificationManager.notify(1, builder.build())
    }
}