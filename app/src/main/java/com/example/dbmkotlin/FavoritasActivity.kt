package com.example.dbmkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbmkotlin.Model.AdapterFM
import com.example.dbmkotlin.Model.DBOpenHelper
import com.example.dbmkotlin.Model.MovieFavorite
import kotlinx.android.synthetic.main.activity_favoritas.*
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
        items.clear()
        val db= DBOpenHelper.getInstance(this)
        db?.use {
           select("Favoritos").exec {
                if(this.count>0){
                    items.clear()
               this.moveToFirst()
               do{

                   items.add(MovieFavorite(
                             this.getInt(0),
                       this.getString(1)?:"",
                       this.getString(3)?:"",
                       this.getString(2)?:"",
                       this.getString(4)?:"",
                            this.getDouble(5)))

               }while (this.moveToNext())}

           }
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
