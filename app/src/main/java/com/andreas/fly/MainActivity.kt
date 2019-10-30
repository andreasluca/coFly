package com.andreas.fly

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.andreas.fly.Constants.Companion.INTENT_FLIGHTS_KEY
import com.andreas.fly.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SearchFlightsViewInterface, AdapterView.OnItemSelectedListener {

    companion object {
        private const val LOG_TAG = "MainActivity"
        private val AIRPORT_CITIES = arrayListOf("Atlanta", "Beijing", "London", "Chicago", "Tokyo", "Los Angeles", "Paris", "Dallas", "Frankfurt", "Hong Kong", "Denver", "Dubai", "Amsterdam", "Madrid", "Bangkok", "New York", "Singapore", "Las Vegas", "Shanghai", "San Francisco", "Phoenix", "Houston", "Miami", "Munich", "Kuala", "Rome", "Istanbul", "Sydney", "Orlando", "Barcelona", "London", "Toronto", "Minneapolis", "Seattle", "Detroit", "Philadelphia", "Sao Paulo", "Boston", "Paris")
    }

    private lateinit var mainPresenter: MainPresenter
    private lateinit var airportCityAdapter: ArrayAdapter<String>

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
        supportActionBar!!.title = searchYourFlight

        mainPresenter = Injector.injectMainPresenter()
        mainPresenter.onViewAttached(this)

        val retrofitCall: Button = searchButton

        // Mock TravelGroup
        val passenger1 = Passenger("Andreas", "BCN")
        val passenger2 = Passenger("Benjamin", "MAD")
        val passenger3 = Passenger("Ruben", "LHR")
        val passenger4 = Passenger("Marc", "CDG")
        val brothers = arrayOf(passenger1, passenger2, passenger3, passenger4)
        val travelGroup1 = TravelGroup("Brothers", brothers)

        val cal = Calendar.getInstance()

        val departureSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            departureDateId.text = sdf.format(cal.time)
        }

        val arrivalSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            arrivalDateId.text = sdf.format(cal.time)

        }

        departureDateId.setOnClickListener {
            DatePickerDialog(this@MainActivity, departureSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        arrivalDateId.setOnClickListener {
            DatePickerDialog(this@MainActivity, arrivalSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Set spinner
        arrivalSpinner.onItemSelectedListener = this
        airportCityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, AIRPORT_CITIES)
        // Set layout to use when the list of choices appear
        airportCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        arrivalSpinner.adapter = airportCityAdapter
        // Set default value
        val defaultArrivalAirportPos = airportCityAdapter.getPosition("New York")
        arrivalSpinner.setSelection(defaultArrivalAirportPos)
        destinationTextId.text = getIataCodeFor("New York")

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

                mainPresenter.onUserClickedSearchFlights(requestFlights.toTypedArray())
            }
        }
    }

    override fun onDestroy() {
        mainPresenter.onViewDetached()
        super.onDestroy()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        destinationTextId.text = getIataCodeFor(airportCityAdapter.getItem(p2)!!)
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
        val flightsIntent = Intent(this, FlightsActivity::class.java)
        flightsIntent.putParcelableArrayListExtra(INTENT_FLIGHTS_KEY, services)
        startActivity(flightsIntent)
    }

    private fun getIataCodeFor(airportCity: String): String {
        when (airportCity) {
            "Atlanta" -> return "ATL"
            "Beijing" -> return "PEK"
            "London" -> return "LHR"
            "Chicago" -> return "ORD"
            "Tokyo" -> return "HND"
            "Los Angeles" -> return "LAX"
            "Paris" -> return "CDG"
            "Dallas" -> return "DFW"
            "Frankfurt" -> return "FRA"
            "Hong Kong" -> return "HKG"
            "Denver" -> return "DEN"
            "Dubai" -> return "DXB"
            "Amsterdam" -> return "AMS"
            "Madrid" -> return "MAD"
            "Bangkok" -> return "BKK"
            "New York" -> return "JFK"
            "Singapore" -> return "SIN"
            "Las Vegas" -> return "LAS"
            "Shanghai" -> return "PVG"
            "San Francisco" -> return "SFO"
            "Phoenix" -> return "PHX"
            "Houston" -> return "IAH"
            "Miami" -> return "MIA"
            "Munich" -> return "MUC"
            "Kuala" -> return "KUL"
            "Rome" -> return "FCO"
            "Istanbul" -> return "IST"
            "Sydney" -> return "SYD"
            "Orlando" -> return "MCO"
            "Barcelona" -> return "BCN"
            "Toronto" -> return "YYZ"
            "Minneapolis" -> return "MSP"
            "Seattle" -> return "SEA"
            "Detroit" -> return "DTW"
            "Philadelphia" -> return "PHL"
            "Sao Paulo" -> return "GRU"
            "Boston" -> return "BOS"
            else -> return "LHR"
        }
    }
}
