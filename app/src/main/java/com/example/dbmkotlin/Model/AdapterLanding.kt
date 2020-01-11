package com.example.dbmkotlin.Model

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmkotlin.DetailActivity
import com.example.dbmkotlin.R
import com.example.dbmkotlin.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemlanding.view.*

data class AdapterLanding( val data: List<ResultsItem>): RecyclerView.Adapter<AdapterLanding.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(parent.inflate(R.layout.itemlanding))

    override fun getItemCount(): Int = data.size?:0

    override fun onBindViewHolder(holder: Holder, position: Int) {



        holder.bindVIew(data[position])

    }

    class Holder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bindVIew(resultsItem: ResultsItem) {
            with(resultsItem) {
                val animation=AnimationUtils.loadAnimation(itemView.context as Activity,
                    R.anim.fade_transition_animation
                )
                itemView.startAnimation(animation)
                itemView.txttitleitem.text = title
                itemView.txtyearitem.text = releaseDate
                itemView.txtvoteitem.text = voteAverage.toString()
                var Urll: String = "https://image.tmdb.org/t/p/w500"
                Picasso.with(itemView.context).load(Urll + backdropPath).into(itemView.imgitem)

                itemView.setOnClickListener {
                    val ubi="detail"
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("titulo", originalTitle)
                    intent.putExtra("año", releaseDate)
                    intent.putExtra("votes", voteAverage)
                    intent.putExtra("desc", overview)
                    intent.putExtra("imagen", backdropPath)
                    intent.putExtra("id", id)
                    intent.putExtra("ubicacion", ubi)



                    val p1: androidx.core.util.Pair<View, String> =
                        androidx.core.util.Pair.create(itemView.imgitem, "transitionImg")
                    val p2: androidx.core.util.Pair<View, String> =
                        androidx.core.util.Pair.create(itemView.txttitleitem, "transitionTitle")
                    val p3: androidx.core.util.Pair<View, String> =
                        androidx.core.util.Pair.create(itemView.txtvoteitem, "transitionVote")
                    val p4: androidx.core.util.Pair<View, String> =
                        androidx.core.util.Pair.create(itemView.txtyearitem, "transitionYear")
                    val options: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            p1,
                            p2,
                            p3,
                            p4
                        )
                    itemView.context.startActivity(intent, options.toBundle())


                }

            }
        }
    }

}