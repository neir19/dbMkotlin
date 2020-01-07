package com.example.dbmkotlin

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

            }

        }

    }
}