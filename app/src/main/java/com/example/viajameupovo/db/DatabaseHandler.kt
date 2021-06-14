package com.example.viajameupovo.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.viajameupovo.model.Viagem

//Configurando Banco de dados
class DatabaseHandler (ctx:Context): SQLiteOpenHelper (ctx, DB_NAME, null, DB_VERSION){

    //Dados da tabela
    companion object{
        private val DB_VERSION = 1
        private val DB_NAME = "ListaViagem"
        private val TABLE_NAME = "Viagem"
        private val ID = "Id"
        private val LOCAL = "Local"
        private val CHECKIN = "Checkin"
        private val DATA = "Data"
    }

    //Criando tabela
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $LOCAL TEXT, $CHECKIN TEXT, $DATA TEXT);"
        p0?.execSQL(CREATE_TABLE)
    }

    //Atualiza versão da tabela: Exclui versão anterior e cria nova versão
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    //Adiciona Viagem no banco de dados
    fun addViagem (viagem: Viagem){
        val p0: SQLiteDatabase = writableDatabase //escreve no bd
        val values = ContentValues().apply{
            put(LOCAL, viagem.local)
            put(CHECKIN, viagem.checkin)
            put(DATA, viagem.data)
        }
        //Insere dados da viagem na tabela
        p0.insert(TABLE_NAME, null, values)
    }

    //Lê viagem por ID
    fun getViagem(id: Int): Viagem {
        val p0:SQLiteDatabase = readableDatabase //permite a leitura
        val selectQuery = "SELECT*FROM $TABLE_NAME WHERE $ID = $id;" //seleciona as informações por Id
        val cursor = p0.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        val viagem = populateViagem(cursor) //chama método
        cursor.close()
        return viagem
    }

    //Lê lista de viagens
    fun  getViagemList(): ArrayList<Viagem>{
        val viagemList = ArrayList<Viagem>()
        val p0 = readableDatabase //Leitura
        val selectQuery = "SELECT*FROM $TABLE_NAME ORDER BY $LOCAL;" //Seleciona lista ordenada por local
        val cursor = p0.rawQuery(selectQuery, null)
        if(cursor != null){ //Se diferente de nulo
            if(cursor.moveToFirst()){ //move para a primeira posição
                do{ //Faça enquanto
                    val viagem = populateViagem(cursor) //pega as viagens
                    viagemList.add(viagem) //Adiciona na lista
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return viagemList //retorna lista populada
    }

    //Atualizar viagem
    fun updateViagem (viagem: Viagem){
        val p0 = writableDatabase //Escrever na tabela
        val values = ContentValues().apply {
            put(LOCAL, viagem.local)
            put(CHECKIN, viagem.checkin)
            put(DATA, viagem.data)
        }
        //atualiza por id
        p0.update(TABLE_NAME, values, "$ID=?", arrayOf(viagem.id.toString()))
    }

    //Deletar viagem
    fun delViagem(id: Int){
        val p0 = writableDatabase
        //Deleta por id
        p0.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
    }

    //Popula objeto viagem
    fun populateViagem(cursor: Cursor): Viagem{
        val viagem = Viagem()
        viagem.id = cursor.getInt(cursor.getColumnIndex(ID)) //pega id
        viagem.local = cursor.getString(cursor.getColumnIndex(LOCAL)) //pega local
        viagem.checkin = cursor.getString(cursor.getColumnIndex(CHECKIN)) //pega check-in
        viagem.data = cursor.getString(cursor.getColumnIndex(DATA)) //pega data
        return viagem //retorna o objeto populado
    }
}
