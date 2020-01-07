package com.example.dbmkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmkotlin.Model.MovieFavorite
import kotlinx.android.synthetic.main.activity_favoritas.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.select

class FavoritasActivity : AppCompatActivity() {
    val items= arrayListOf<MovieFavorite>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritas)


            recyF.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterFM(items)
            recyF.adapter = adapter




    }

    override fun onResume() {
        super.onResume()
        val db= DBOpenHelper.getInstance(this)
        db?.use {
           select("Favoritos").exec {
                if(this.count>0){
               this.moveToFirst()
               do{
                   items.add(MovieFavorite(
                       this.getString(1)?:"",
                       this.getString(3)?:"",
                       this.getString(2)?:"",
                            this.getDouble(4)))
               }while (this.moveToNext())}

           }
        }

    }
}
