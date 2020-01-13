package com.example.dbmkotlin

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dbmkotlin.Model.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var page = 1
    val Url = "https://api.themoviedb.org/3/"
    val apikey = "02e4b138dacaf8151088a361d6e75d01"
    lateinit var list: ListaMovie
     var listSQLite= arrayListOf<MovieFavorite>()
    val retrofit = Retrofit.Builder()
        .baseUrl(Url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //VAriables for pagination
    private var isLoding: Boolean = true
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var previus_total = 0
    private var view_threshold = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var layoutManager = GridLayoutManager(this, 2)

        recycler.layoutManager = layoutManager

        btnseeFavorites.setOnClickListener {
            val intent = Intent(this, FavoritasActivity::class.java)
            startActivity(intent)
        }
        btnsearch.setOnClickListener {
            val fm=supportFragmentManager
            val myfragment= Myfragment()
            myfragment.show(fm,"simple Fragment")
        }
        if(accessInternet()) {
            progressBar.visibility = View.VISIBLE
            val endpoints = retrofit.create(Endpoints::class.java)
            endpoints.getList(apikey, page)
                .enqueue(object : Callback<Movies> {
                    override fun onFailure(call: Call<Movies>, t: Throwable) {
                    }
                    override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                        if (response.isSuccessful) {
                            llenarReccler(response.body()?.results)
                            progressBar.visibility = View.GONE
                        }
                    }
                })
                    //pagination
            scrollPagination(layoutManager)
        }else{
            val adapter = AdapterFM(listSQLite )
            recycler.setHasFixedSize(true)
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation)
            recycler.startAnimation(animation)
            recycler.adapter = adapter
        }
    }
    private fun llenarReccler(results: List<ResultsItem?>?) {
        recycler.setHasFixedSize(true)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation)
        recycler.startAnimation(animation)
        list = ListaMovie(results as ArrayList<ResultsItem>)
        val adapter = AdapterLanding(list.lista)
        recycler.adapter = adapter




    }
    private fun performPagenation(retrofit: Retrofit) {
        progressBar.visibility = View.VISIBLE
        val endpoints = retrofit.create(Endpoints::class.java)
        endpoints.getList(apikey, page)
            .enqueue(object : Callback<Movies> {
                override fun onFailure(call: Call<Movies>, t: Throwable) {

                }

                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if (response.isSuccessful) {
                        list.addItem(response.body()?.results as ArrayList<ResultsItem>)
                        llenarSQLiteP(list.lista)
                        Log.e("pagina", "$page")
                    }
                    progressBar.visibility = View.GONE
                }
            })
    }
    private  fun llenarSQLiteP(list: List<ResultsItem>){
        val db= DBOpenHelper.getInstance(this)
        var count: Int=0
        db?.use {
            select("Popular").exec {
                for(item:ResultsItem in list){
                if (this.count != 0) {


                    this.moveToFirst()
                    do {
                        if (item.id == this.getInt(0)) {
                            count++
                            break
                        } else {
                            count = 0
                        }
                    }while (this.moveToNext())

                    if(count==0) {
                        val idPr = "id" to item.id
                        val namePr = "titulo" to item.originalTitle
                        val yearPr = "año" to item.releaseDate
                        val descPr = "desc" to item.overview
                        val imgPr  ="img" to item.backdropPath
                        val votesPr = "votos" to item.voteAverage
                        insert("Popular", idPr, namePr, yearPr, descPr,imgPr, votesPr)
                    }
                    }else{
                    val idPr = "id" to item.id
                    val namePr = "titulo" to item.originalTitle
                    val yearPr = "año" to item.releaseDate
                    val descPr = "desc" to item.overview
                    val imgPr  ="img" to item.backdropPath
                    val votesPr = "votos" to item.voteAverage
                    insert("Popular", idPr, namePr, yearPr, descPr,imgPr, votesPr)
                }


                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val db= DBOpenHelper.getInstance(this)
        db?.use {
            select("Popular").exec {
                Log.e("tam","${this.count}")
                if(this.count!=0){
                    listSQLite.clear()
                    this.moveToFirst()
                    do{
                        listSQLite.add(MovieFavorite(
                                  this.getInt(0),
                            this.getString(1)?:"",
                            this.getString(3)?:"",
                            this.getString(2)?:"",
                            this.getString(4)?:"",
                                 this.getDouble(5)))
                    }while (this.moveToNext())
                    Log.e("tam2","${listSQLite.size}")
                }
            }
        }
    }
    private fun accessInternet(): Boolean {
        val cm=this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo:NetworkInfo?=cm.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
    fun scrollPagination(layoutManager:GridLayoutManager){
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if (dy > 0) {
                    if (isLoding) {
                        if (totalItemCount > previus_total) {
                            isLoding = false
                            previus_total = totalItemCount
                        }
                    }
                    if ((!isLoding) && ((totalItemCount - visibleItemCount) <= (pastVisibleItems + view_threshold))) {
                        page++
                        performPagenation(retrofit)//paginacion
                        isLoding = true
                    }
                }
            }
        })
    }
}
