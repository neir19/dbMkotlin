package com.example.dbmkotlin

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmkotlin.Model.MovieFavorite
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
                itemView.txttitleitem.text = titulo?:""
                itemView.txtyearitem.text = año?:""
                itemView.txtvoteitem.text = votos.toString()?:""

                itemView.setOnClickListener {
                    val iddd= 1223223
                    val stri= "asadsdff"
                    val ubi= "favorite"

                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("titulo", titulo)
                    intent.putExtra("año", año)
                    intent.putExtra("votes", votos)
                    intent.putExtra("desc", desc)
                    intent.putExtra("imagen", stri)
                    intent.putExtra("id", iddd)
                    intent.putExtra("ubicacion",ubi)

                    itemView.context.startActivity(intent)
                }

            }

        }

    }
}