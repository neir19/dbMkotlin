package com.example.dbmkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmkotlin.Model.ListaMovie
import com.example.dbmkotlin.Model.Movies
import com.example.dbmkotlin.Model.ResultsItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var page=1
    val Url= "https://api.themoviedb.org/3/"
    val apikey= "02e4b138dacaf8151088a361d6e75d01"
    lateinit var list: ListaMovie

    //VAriables for pagination

    private  var isLoding: Boolean = true
    private  var  pastVisibleItems=0
    private var visibleItemCount=0
    private var totalItemCount=0
    private  var previus_total=0
    private var view_threshold=20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility= View.VISIBLE
        var layoutManager= GridLayoutManager(this, 2)
        recycler.layoutManager = layoutManager

        btnseeFavorites.setOnClickListener {
            val intent= Intent(this, FavoritasActivity::class.java)
            startActivity(intent)
        }

        val retrofit= Retrofit.Builder()
            .baseUrl(Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         val endpoints= retrofit.create(Endpoints::class.java)
            endpoints.getList(apikey,page)
                .enqueue(object : Callback<Movies> {
                    override fun onFailure(call: Call<Movies>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                        if(response.isSuccessful){
                            llenarReccler(response.body()?.results)
                            progressBar.visibility=View.GONE


                        }
                    }

                })
                recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){


                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()


                        if (dy > 0) {
                            if (isLoding) {
                                Log.e("entro1", "entro")
                                if (totalItemCount > previus_total) {
                                    Log.e("entro2", "entro")
                                    isLoding = false
                                    previus_total = totalItemCount
                                }

                            }



                            if ((!isLoding ) && ((totalItemCount - visibleItemCount) <= (pastVisibleItems + view_threshold))) {
                                Log.e("entro3", "entro")

                                page++
                                performPagenation(retrofit)
                                isLoding = true

                            }
                        }

                    }


                })



    }

    private fun llenarReccler(results: List<ResultsItem?>?) {


        list= ListaMovie(results as ArrayList<ResultsItem>)
        val adapter =AdapterLanding(list.lista)
        recycler.setHasFixedSize(true)
        recycler.adapter= adapter
    }
    private fun performPagenation(retrofit: Retrofit){
        progressBar.visibility= View.VISIBLE
        val endpoints= retrofit.create(Endpoints::class.java)
        endpoints.getList(apikey,page)
            .enqueue(object : Callback<Movies> {
                override fun onFailure(call: Call<Movies>, t: Throwable) {

                }

                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if(response.isSuccessful){
                    list.addItem(response.body()?.results as ArrayList<ResultsItem>)
                        Log.e("pagina","$page")



                    }
                    progressBar.visibility= View.GONE
                }

            })

}
}
