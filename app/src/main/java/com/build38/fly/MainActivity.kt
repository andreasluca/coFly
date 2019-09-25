package com.build38.fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.build38.fly.model.RequestFlight
import com.build38.fly.model.Service
import com.build38.fly.repository.AmadeusShoppingApi
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
                val requestFlight = RequestFlight("BCN", "CLJ", "23/10/2019", "25/12/2019", true)
                mainPresenter.onUserClickedSearchFlight(requestFlight)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFlightsNotFoundFailure() {
        Log.d(LOG_TAG, "NOT FOUND!!!!!!!!!!!!!")
    }

    override fun showUnexpectedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
