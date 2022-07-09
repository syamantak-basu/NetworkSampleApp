package com.example.networksampleapplication.model

data class Picture(val date: String, val explanation: String, val media_type: String, val service_version: String,
                   val title: String, val url: String, val hdurl: String, var isLiked : Boolean)