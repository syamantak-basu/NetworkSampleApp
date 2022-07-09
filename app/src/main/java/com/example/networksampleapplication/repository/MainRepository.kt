package com.example.networksampleapplication.repository

import com.example.networksampleapplication.retrofit.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getNasaAPOD() = retrofitService.getNasaAPOD()
}