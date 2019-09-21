package com.build38.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.build38.fly.model.amadeus.AmadeusOfferItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "AMADEUS"
    }

    private lateinit var retrofitCall: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitCall = searchButton

        retrofitCall.setOnClickListener {
            val amadeusApi = Injector(this).provideAmadeusApi()
            amadeusApi
                .getRoundTripFlights("BCN", "MAD", "2019-10-01", "2019-11-01")
                .enqueue(object : Callback<List<AmadeusOfferItem>> {
                    override fun onResponse(call: Call<List<AmadeusOfferItem>>, response: Response<List<AmadeusOfferItem>>) {
                        Log.d(LOG_TAG, "I AM ALIVE")
                    }

                    override fun onFailure(call: Call<List<AmadeusOfferItem>>, t: Throwable) {
                        Log.d(LOG_TAG, ":((((((((((")
                    }
                })
        }
    }
}
