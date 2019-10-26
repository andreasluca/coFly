package com.build38.fly

import com.build38.fly.model.Service

interface SearchFlightsViewInterface {

    fun showFlightFoundSuccess(service: Service)
    fun showFlightsFoundSuccess(services: Array<Service>)
    fun showFlightsNotFoundFailure()
    fun showUnexpectedError()
}
