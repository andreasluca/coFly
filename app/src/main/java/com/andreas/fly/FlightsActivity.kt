package com.andreas.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.andreas.fly.Constants.Companion.INTENT_FLIGHTS_KEY
import com.andreas.fly.model.Service

class FlightsActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "FlightsActivity"
    }

    lateinit var services: ArrayList<Service>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        services = intent.getParcelableArrayListExtra<Service>(INTENT_FLIGHTS_KEY)

        // Set action bar
        val flyingTo = "Flying to ${services[0].departureFlight.to}"
        supportActionBar!!.title = flyingTo


        for (service in services) {
            Log.d(LOG_TAG, service.toString())
        }
    }
}
