package com.andreas.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.andreas.fly.model.RequestFlight
import com.andreas.fly.model.Service
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchFlightsViewInterface {

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter = Injector.injectMainPresenter()
        mainPresenter.onViewAttached(this)

        val retrofitCall: Button = searchButton

        retrofitCall.setOnClickListener {
            GlobalScope.launch {
                val requestFlight = RequestFlight("BCN", "JFK", "2019-10-15", "2019-11-13", true)
                // One flight: mainPresenter.onUserClickedSearchFlight(requestFlight)

                val requestFlight2 = RequestFlight("BCN", "MAD", "2019-10-15", "2019-11-13", true)
                val requestFlights = arrayOf(requestFlight, requestFlight2)
                mainPresenter.onUserClickedSearchFlights(requestFlights)
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
    }

    override fun showFlightsFoundSuccess(services: Array<Service>) {
        Log.d(LOG_TAG, "YEAH!!!!!!!!!!!!!!!!!!!")
        for (service in services) {
            Log.d(LOG_TAG, service.toString())
        }
    }

    override fun showFlightsNotFoundFailure() {
        Log.d(LOG_TAG, "NOT FOUND!!!!!!!!!!!!!")
    }

    override fun showUnexpectedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
