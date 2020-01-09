package com.example.dbmkotlin

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_descr.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        val db= DBOpenHelper.getInstance(this)

        intent.extras.let{
            var Urlll:String= "https://image.tmdb.org/t/p/w500"
            val titl =it.getString("titulo")
            val yer=it.getString("año")
            val descr = it.getString("desc")
            val vote= it.getDouble("votes")
            val idd= it.getInt("id")
            val ubi=it.getString("ubicacion")
            txttitledetail.text=it.getString("titulo")
            txtyeardetail.text=it.getString("año")
            txtdescdetail.text= it.getString("desc")
            txtvotedetail.text=it.getDouble("votes").toString()
            Picasso.with(this).load(Urlll+it.getString("imagen")).into(imgdetail)
            btnFavorites.setOnClickListener {

                if (ubi.equals("detail")){



                val interpolator = AnimationUtils.loadInterpolator(
                    baseContext,
                    android.R.interpolator.fast_out_slow_in
                )
                btnFavorites.animate()
                    .rotation(180f)
                    .setInterpolator(interpolator)
                    .setDuration(600)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            btnFavorites.animate()
                                .rotation(0f)
                                .setInterpolator(interpolator)
                                .setDuration(600)
                                .start()
                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationStart(animation: Animator?) {

                        }
                    })


                db?.use {
                    var count = 0
                    select("Favoritos").exec {
                        if (this.count > 0) {


                            this.moveToFirst()
                            do {

                                if (idd == this.getInt(0)) {
                                    count++


                                    break
                                } else {
                                    count = 0
                                }

                            } while (this.moveToNext())
                        }

                    }

                    if (count == 0) {

                        val idPr = "id" to idd
                        val namePr = "titulo" to titl
                        val yearPr = "año" to yer
                        val descPr = "desc" to descr
                        val votesPr = "votos" to vote
                        insert("Favoritos", idPr, namePr, yearPr, descPr, votesPr)
                    }

                }
                Toast.makeText(this, "se guardo en favoritos", Toast.LENGTH_SHORT).show()
            }
                else if(ubi.equals("favorite")){

                    db?.use {
                       val  numRowsDelected= delete("Favoritos","id={iid}","iid" to idd)

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                       }
                    }
                }
                }

            }




