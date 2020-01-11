package com.example.dbmkotlin.Model

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmkotlin.DetailActivity
import com.example.dbmkotlin.FavoritasActivity
import com.example.dbmkotlin.R
import com.example.dbmkotlin.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemlanding.view.*

class AdapterFM(val data: List<MovieFavorite>):RecyclerView.Adapter<AdapterFM.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(parent?.inflate(R.layout.itemlanding))

    override fun getItemCount(): Int = data.size?:0

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bindView(data[position])
    }


    class  Holder(ItemView : View?):RecyclerView.ViewHolder(ItemView!!){
        fun bindView(movieFavorite: MovieFavorite){
            with(movieFavorite){
                var Urll: String = "https://image.tmdb.org/t/p/w500"
                itemView.txttitleitem.text = titulo?:""
                itemView.txtyearitem.text = año?:""
                itemView.txtvoteitem.text = votos.toString()?:""
                Picasso.with(itemView.context as Activity).load(Urll+img).error(R.mipmap.descarga).into(itemView.imgitem)

                itemView.setOnClickListener {


                    val ubi= "favorite"

                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("titulo", titulo)
                    intent.putExtra("año", año)
                    intent.putExtra("votes", votos)
                    intent.putExtra("desc", desc)
                    intent.putExtra("imagen", img)
                    intent.putExtra("id", id)
                    intent.putExtra("ubicacion",ubi)

                    itemView.context.startActivity(intent)


                }

            }

        }

    }
}