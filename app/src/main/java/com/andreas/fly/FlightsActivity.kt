package com.andreas.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreas.fly.Constants.Companion.INTENT_FLIGHTS_KEY
import com.andreas.fly.model.Service
import kotlinx.android.synthetic.main.activity_flights.*

class FlightsActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "FlightsActivity"
    }

    lateinit var flightsRecyclerView: RecyclerView
    lateinit var services: ArrayList<Service>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        services = intent.getParcelableArrayListExtra<Service>(INTENT_FLIGHTS_KEY)

        // Set action bar
        val flyingTo = "Flying to ${services[0].departureFlight.to}"
        supportActionBar!!.title = flyingTo

        // Populate recycler view
        flightsRecyclerView = flights_recyclerview
        flightsRecyclerView.layoutManager = LinearLayoutManager(this)
        flightsRecyclerView.adapter = FlightsRecyclerViewAdapter(services)

        // TODO: remove
        for (service in services) {
            Log.d(LOG_TAG, service.toString())
        }
    }
}
