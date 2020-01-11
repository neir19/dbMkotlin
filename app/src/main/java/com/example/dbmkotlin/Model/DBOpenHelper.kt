package com.example.dbmkotlin.Model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx,"MovieDB",null,1) {

    companion object{
        private  var instance: DBOpenHelper?= null
        fun getInstance(ctx: Context): DBOpenHelper?= if(instance ==null){
            instance =
                DBOpenHelper(ctx)
            instance
        }else{
            instance
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val id= "id" to INTEGER
        val title= "titulo" to TEXT
        val year ="año" to TEXT
        val desc="desc" to TEXT
        val img="img" to TEXT
        val votes="votos" to REAL


        db?.createTable("Favoritos",true,id,title,year,desc,img,votes)
        db?.createTable("Popular",true,id,title,year,desc,img,votes)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("Favoritos",true)
        db?.dropTable("Popular",true)
    }
    val Context.database: DBOpenHelper?
        get()= getInstance(applicationContext)
}