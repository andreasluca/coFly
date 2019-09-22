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
            amadeusApi
                .getRoundTripFlights("BCN", "CLJ", "2019-09-23", "2019-11-01")
                .enqueue(object : Callback<AmadeusResponse> {
                    override fun onResponse(call: Call<AmadeusResponse>, response: Response<AmadeusResponse>) {
                        Log.d(LOG_TAG, "I AM ALIVE")
                        val resp = response.body()?.data?.get(0)?.offerItems?.get(0)?.price
                        Log.d(LOG_TAG, "Price: $resp")
                    }

                    override fun onFailure(call: Call<AmadeusResponse>, t: Throwable) {
                        Log.d(LOG_TAG, ":((((((((((")
                        Log.d(LOG_TAG, t.message?:"Thrown error without message")
                        t.printStackTrace()
                    }
                })
        }
    }
}
