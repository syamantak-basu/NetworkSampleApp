package com.example.networksampleapplication.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.networksampleapplication.R
import com.example.networksampleapplication.databinding.ActivityMainBinding
import com.example.networksampleapplication.model.Picture
import com.example.networksampleapplication.repository.MainRepository
import com.example.networksampleapplication.retrofit.RetrofitService
import com.example.networksampleapplication.utility.Utils
import com.example.networksampleapplication.viewmodel.MainViewModel
import com.example.networksampleapplication.viewmodel.MyViewModelFactory
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MyViewModelFactory(
                                        MainRepository(retrofitService))).get(MainViewModel::class.java)


        viewModel.pictureData.observe(this, Observer {
            Log.d(TAG, "on success: $it")
            setUiData(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "on failed: $it")
        })

        if (Utils.isInternetAvailable(MainActivity@this)) {
            viewModel.getNasaAPOD()
        } else {
           Toast.makeText(this,"No Internet",Toast.LENGTH_LONG).show()
        }
    }

    fun setUiData(picture : Picture){
        binding.dateValue.text = picture.date
        binding.titleValue.text = picture.title
        binding.expValue.text = picture.explanation
        Glide.with(this).load(picture.url).into(binding.imgNasapic)

        binding.btnLike.setOnClickListener(View.OnClickListener {
            if(!picture.isLiked){
                picture.isLiked = true
                binding.btnLike.setImageResource(R.drawable.favorite_actv)
            }else{
                picture.isLiked = false
                binding.btnLike.setImageResource(R.drawable.favorite_deactv)
            }
        })

        binding.btnCalender.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(MainActivity@this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                Log.d(TAG, "$dayOfMonth.$monthOfYear.$year")

            }, year, month, day)

            dpd.show()
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}