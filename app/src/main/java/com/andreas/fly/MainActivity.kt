package com.andreas.fly

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.andreas.fly.Constants.Companion.INTENT_FLIGHTS_KEY
import com.andreas.fly.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SearchFlightsViewInterface, AdapterView.OnItemSelectedListener {

    companion object {
        private const val LOG_TAG = "MainActivity"
        private val AIRPORT_CITIES = arrayListOf("Atlanta", "Beijing", "London", "Chicago", "Tokyo", "Los Angeles", "Paris", "Dallas", "Frankfurt", "Hong Kong", "Denver", "Dubai", "Amsterdam", "Madrid", "Bangkok", "New York", "Singapore", "Las Vegas", "Shanghai", "San Francisco", "Phoenix", "Houston", "Miami", "Munich", "Kuala", "Rome", "Istanbul", "Sydney", "Orlando", "Barcelona", "London", "Toronto", "Minneapolis", "Seattle", "Detroit", "Philadelphia", "Sao Paulo", "Boston", "Paris")

        // Mock TravelGroups
        val brother1 = Passenger("Andreas", "BCN")
        val brother2 = Passenger("Benjamin", "MAD")
        val brother3 = Passenger("Ruben", "LHR")
        val brother4 = Passenger("Marc", "CDG")
        val brothers = arrayOf(brother1, brother2, brother3, brother4)
        val brothersTravelGroup = TravelGroup("Brothers", brothers)

        val passenger1 = Passenger("Andreas", "BCN")
        val passenger2 = Passenger("Alex", "PEK")
        val passenger3 = Passenger("Sergi", "HND")
        val passenger4 = Passenger("Juan", "DFW")
        val passenger5 = Passenger("Daniel", "MUC")
        val friends = arrayOf(passenger1, passenger2, passenger3, passenger4, passenger5)
        val friendsTravelGroup = TravelGroup("Friends", friends)

        val workMate1 = Passenger("Andreas", "BCN")
        val workMate2 = Passenger("Marc", "MAD")
        val work = arrayOf(workMate1, workMate2)
        val workTravelGroup = TravelGroup("Work", work)

        val availableTravelGroups = arrayListOf(brothersTravelGroup, friendsTravelGroup, workTravelGroup)
        val availableTravelGroupsNames = availableTravelGroups.map { it.name }.toTypedArray()
    }

    private lateinit var mainPresenter: MainPresenter
    private lateinit var airportCityAdapter: ArrayAdapter<String>
    private lateinit var destinationText: TextView
    private lateinit var departureDate: TextView
    private lateinit var arrivalDate: TextView
    private lateinit var fromText: TextView
    private lateinit var amountOfPeopleText: TextView
    private lateinit var busyDialog: BusyDialogFragment

    private var defaultTravelGroup = brothersTravelGroup

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
        destinationText = destinationTextId
        departureDate = departureDateId
        arrivalDate = arrivalDateId
        fromText = fromTextId
        amountOfPeopleText = amountOfPeopleTextId

        // Default Travel Group
        fromText.text = defaultTravelGroup.name
        amountOfPeopleText.text = "${defaultTravelGroup.passengers.size} PEOPLE"

        fromText.setOnClickListener {
            showTravelGroupPicker()
        }

        // Set calendar pickers
        val cal = Calendar.getInstance()

        val departureSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            departureDate.text = sdf.format(cal.time)
        }

        val arrivalSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            arrivalDate.text = sdf.format(cal.time)

        }

        departureDate.setOnClickListener {
            DatePickerDialog(this@MainActivity, departureSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        arrivalDate.setOnClickListener {
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
        destinationText.text = getIataCodeFor("New York")

        retrofitCall.setOnClickListener {
            busyDialog = BusyDialogFragment.show(supportFragmentManager)

            GlobalScope.launch {
                val to = destinationText.text.toString()
                val departOn = departureDate.text.toString()
                val returnOn = arrivalDate.text.toString()
                val requestFlights = mutableListOf<RequestFlight>()

                for (passenger in defaultTravelGroup.passengers) {
                    requestFlights.add(
                        RequestFlight(passenger.departure, to, departOn, returnOn, true)
                    )
                }
                mainPresenter.onUserClickedSearchFlights(requestFlights.toTypedArray())
            }
        }
    }

    private fun showTravelGroupPicker() {
        // Setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a travel group")

        // Set desired travel group
        builder.setItems(availableTravelGroupsNames) { dialog, which ->
            when (which) {
                0 -> {
                    defaultTravelGroup = brothersTravelGroup

                }
                1 -> {
                    defaultTravelGroup = friendsTravelGroup
                }
                2 -> {
                    defaultTravelGroup = workTravelGroup
                }
            }
            fromText.text = defaultTravelGroup.name
            amountOfPeopleText.text = "${defaultTravelGroup.passengers.size} PEOPLE"
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        mainPresenter.onViewDetached()
        super.onDestroy()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.e(LOG_TAG, "Nothing selected")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        destinationText.text = getIataCodeFor(airportCityAdapter.getItem(p2)!!)
    }

    override fun showFlightFoundSuccess(service: Service) {
        busyDialog.dismiss()
        Log.d(LOG_TAG, "YEAH!!!!!!!!!!!!!!!!!!!")
        Log.d(LOG_TAG, service.toString())
        startFlightsActivity(arrayListOf(service))
    }

    override fun showFlightsFoundSuccess(services: Array<Service>) {
        busyDialog.dismiss()
        Log.d(LOG_TAG, "YEAH!!!!!!!!!!!!!!!!!!!")
        startFlightsActivity(services.toCollection(ArrayList()))
    }

    override fun showFlightsNotFoundFailure() {
        busyDialog.dismiss()
        Log.d(LOG_TAG, "NOT FOUND!!!!!!!!!!!!!")
        GlobalScope.launch(Dispatchers.Main) {
            showFailureDialog()
        }
    }

    override fun showUnexpectedError() {
        busyDialog.dismiss()
        showFailureDialog()
    }

    private fun showFailureDialog() {
        busyDialog.dismiss()
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage("An error occurred while searching for the flights.")
            setPositiveButton(
                "Ok"
            ) { _, _ -> }
        }.show()
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
