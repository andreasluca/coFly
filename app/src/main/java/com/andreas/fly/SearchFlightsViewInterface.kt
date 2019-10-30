package com.andreas.fly

import com.andreas.fly.model.Service

interface SearchFlightsViewInterface {

    fun showFlightFoundSuccess(service: Service)
    fun showFlightsFoundSuccess(services: Array<Service>)
    fun showFlightsNotFoundFailure()
    fun showUnexpectedError()
}
