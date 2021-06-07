package com.example.minhamala.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.minhamala.model.Viagem


class DatabaseHandler (ctx:Context): SQLiteOpenHelper (ctx, DB_NAME, null, DB_VERSION){

    companion object{
        private val DB_VERSION = 1
        private val DB_NAME = "Caduware"
        private val TABLE_NAME = "Viagem"
        private val ID = "Id"
        private val LOCAL = "Local"
        private val CHECKIN = "Checkin"
        private val DATA = "Data"
    }

    //Cria tabela
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $LOCAL TEXT, $CHECKIN TEXT, $DATA TEXT);"
        p0?.execSQL(CREATE_TABLE)
    }

    //Atualiza versão da tabela e exclui anterior
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    //Adiciona Viagem no bd
    fun addViagem (viagem: Viagem){
        val p0: SQLiteDatabase = writableDatabase
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
        val p0:SQLiteDatabase = readableDatabase
        val selectQuery = "SELECT*FROM $TABLE_NAME WHERE $ID = $id;"
        val cursor = p0.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        val viagem = populateViagem(cursor)
        cursor.close()
        return viagem
    }

    //Lê lista de viagens
    fun  getViagemList(): ArrayList<Viagem>{
        val viagemList = ArrayList<Viagem>()
        val p0 = readableDatabase //Leitura
        val selectQuery = "SELECT*FROM $TABLE_NAME ORDER BY $LOCAL;" //Lista ordenada
        val cursor = p0.rawQuery(selectQuery, null)
        if(cursor != null){ //Se diferente de nulo
            if(cursor.moveToFirst()){ //move para a primeira posição
                do{
                    val viagem = populateViagem(cursor)
                    viagemList.add(viagem)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return viagemList
    }

    //Atualizar viagem
    fun updateViagem (viagem: Viagem){
        val p0 = writableDatabase //Escrever na tabela
        val values = ContentValues().apply {
            put(LOCAL, viagem.local)
            put(CHECKIN, viagem.checkin)
            put(DATA, viagem.data)
        }
        p0.update(TABLE_NAME, values, "$ID=?", arrayOf(viagem.id.toString()))
    }

    //Deletar viagem
    fun delViagem(id: Int){
        val p0 = writableDatabase
        p0.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
    }


    //Popula objeto viagem
    //Popular objeto
    fun populateViagem(cursor: Cursor): Viagem{
        val viagem = Viagem()
        viagem.id = cursor.getInt(cursor.getColumnIndex(ID))
        viagem.local = cursor.getString(cursor.getColumnIndex(LOCAL))
        viagem.checkin = cursor.getString(cursor.getColumnIndex(CHECKIN))
        viagem.data = cursor.getString(cursor.getColumnIndex(DATA))
        return viagem
    }
}
