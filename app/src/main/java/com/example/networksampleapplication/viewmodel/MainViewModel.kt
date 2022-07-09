package com.example.networksampleapplication.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networksampleapplication.repository.MainRepository
import com.example.networksampleapplication.model.Picture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val pictureData = MutableLiveData<Picture>()
    val errorMessage = MutableLiveData<String>()

    fun getNasaAPOD() {
        val response = repository.getNasaAPOD()
        response.enqueue(object : Callback<Picture> {
            override fun onResponse(call: Call<Picture>, response: Response<Picture>) {
                pictureData.postValue(response.body())
            }
            override fun onFailure(call: Call<Picture>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}