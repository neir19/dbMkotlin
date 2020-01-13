package com.example.dbmkotlin

import android.app.Activity
import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import android.system.Os.close
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dbmkotlin.Model.DBOpenHelper
import com.example.dbmkotlin.Model.ResultsItem
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

fun Activity.toast(mensaje: String){
    Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
}
fun ViewGroup.inflate(layoutid: Int): View {
    return LayoutInflater.from(context).inflate(layoutid, this , false)
}
fun insertarenTable(ctx: Context, table: String, idd:Int,titl:String,yer:String,descr:String,imgg:String,vote:Double) {
    val db = DBOpenHelper.getInstance(ctx)

    db?.use {
        select(table).exec {
            val idPr = "id" to idd
            val namePr = "titulo" to titl
            val yearPr = "año" to yer
            val descPr = "desc" to descr
            val imgPr = "img" to imgg
            val votesPr = "votos" to vote
            insert(table, idPr, namePr, yearPr, descPr, imgPr, votesPr)

        }
    }
}
