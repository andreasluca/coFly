package com.build38.fly

import com.build38.fly.model.RequestFlight
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
        withContext(Dispatchers.Default) {
            val services = repository.getFlights(requestFlight)
            if (services == null) view?.showFlightsNotFoundFailure() else view?.showFlightFoundSuccess(services[0])
        }
    }
}
