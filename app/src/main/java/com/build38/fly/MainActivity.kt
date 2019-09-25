package com.build38.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.build38.fly.model.amadeus.AmadeusResponse
import com.build38.fly.repository.AmadeusShoppingApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "AMADEUS"
    }

    private lateinit var amadeusApi: AmadeusShoppingApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amadeusApi = Injector(this).injectAmadeusShoppingApi()

        val retrofitCall: Button = searchButton

        retrofitCall.setOnClickListener {

        }
    }
}
