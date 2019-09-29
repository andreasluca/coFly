package com.build38.fly

import android.util.Log
import com.build38.fly.model.RequestFlight
import com.build38.fly.model.Service
import com.build38.fly.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPresenter(val repository: Repository) {

    private var view: SearchFlightsViewInterface? = null

    fun onViewAttached(view: SearchFlightsViewInterface) {
        this.view = view
    }

    fun onViewDetached() {
        this.view = null
    }

    suspend fun onUserClickedSearchFlight(requestFlight: RequestFlight) {
        withContext(Dispatchers.IO) {
            val services = repository.getFlights(requestFlight)
            if (services == null) {
                view?.showFlightsNotFoundFailure()
            } else {
                // TODO: select what service to show
                view?.showFlightFoundSuccess(services[0])
            }
        }
    }

    suspend fun onUserClickedSearchFlights(requestFlights: Array<RequestFlight>) {
        val flightServices = mutableListOf<Service>()
        withContext(Dispatchers.IO) {
            for (requestFlight in requestFlights) {
                val services = repository.getFlights(requestFlight)
                if (services != null) {
                    flightServices.add(services[0])
                }
            }
        }
        if (flightServices.isEmpty()) view?.showFlightsNotFoundFailure() else view?.showFlightsFoundSuccess(flightServices.toTypedArray())
    }
}
