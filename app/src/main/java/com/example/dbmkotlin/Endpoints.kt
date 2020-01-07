package com.example.dbmkotlin

import com.example.dbmkotlin.Model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoints{
    @GET("movie/popular")
    fun getList(@Query("api_key") api_key:String,@Query("page") page:Int): Call<Movies>
}