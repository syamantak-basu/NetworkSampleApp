package com.example.networksampleapplication.retrofit

import com.example.networksampleapplication.model.Picture
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("apod?api_key=dha3c2SrzFfFeORcSb3t9jEEHTiT7FKOkHhPKvTP")
    fun getNasaAPOD() : Call<Picture>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.nasa.gov/planetary/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}