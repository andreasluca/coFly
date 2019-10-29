package com.andreas.fly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.andreas.fly.Constants.Companion.INTENT_FLIGHTS_KEY
import com.andreas.fly.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), SearchFlightsViewInterface {

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    private lateinit var mainPresenter: MainPresenter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set action bar
        val searchYourFlight = "Search your flight"
        supportActionBar!!.setTitle(searchYourFlight)

        mainPresenter = Injector.injectMainPresenter()
        mainPresenter.onViewAttached(this)

        val retrofitCall: Button = searchButton

        // Mock TravelGroup
        val passenger1 = Passenger("Andreas", "BCN")
        //val passenger2 = Passenger("Benjamin", "MAD")
        //val passenger3 = Passenger("Ruben", "LHR")
        //val passenger4 = Passenger("Marc", "CDG")
        //val brothers = arrayOf(passenger1, passenger2, passenger3, passenger4)
        val brothers = arrayOf(passenger1)
        val travelGroup1 = TravelGroup("Brothers", brothers)

        retrofitCall.setOnClickListener {
            GlobalScope.launch {
                val to = "JFK"
                val departOn = "2019-11-15"
                val returnOn = "2019-11-25"
                val requestFlights = mutableListOf<RequestFlight>()

                for (passenger in travelGroup1.passengers) {
                    requestFlights.add(
                        RequestFlight(passenger.departure, to, departOn, returnOn, true)
                    )
                }

                // mainPresenter.onUserClickedSearchFlights(requestFlights.toTypedArray())
                val s1 = Service(ResponseFlight("asd", "asdf", "asd", "asd", "asd"), null, 123.1)
                val s2 = Service(ResponseFlight("asd", "asdf", "asd", "asd", "asd"), null, 123.1)
                showFlightsFoundSuccess(arrayOf(s1, s2))
            }
        }
    }

    override fun onDestroy() {
        mainPresenter.onViewDetached()
        super.onDestroy()
    }

    override fun showFlightFoundSuccess(service: Service) {
        Log.d(LOG_TAG, "YEAH!!!!!!!!!!!!!!!!!!!")
        Log.d(LOG_TAG, service.toString())
        startFlightsActivity(arrayListOf(service))
    }

    override fun showFlightsFoundSuccess(services: Array<Service>) {
        Log.d(LOG_TAG, "YEAH!!!!!!!!!!!!!!!!!!!")
        startFlightsActivity(services.toCollection(ArrayList()))
    }

    override fun showFlightsNotFoundFailure() {
        Log.d(LOG_TAG, "NOT FOUND!!!!!!!!!!!!!")
    }

    override fun showUnexpectedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun startFlightsActivity(services: ArrayList<Service>) {
        val s1 = Service(ResponseFlight("asd", "asdf", "asd", "asd", "asd"), null, 123.1)
        val s2 = Service(ResponseFlight("asd", "asdf", "asd", "asd", "asd"), null, 123.1)

        val flightsIntent = Intent(this, FlightsActivity::class.java)
        flightsIntent.putParcelableArrayListExtra(INTENT_FLIGHTS_KEY, arrayListOf(s1, s2))
        startActivity(flightsIntent)
    }
}
