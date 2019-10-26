package com.andreas.fly.repository

import com.andreas.fly.model.RequestFlight
import com.andreas.fly.model.Service

interface Repository {

    // TODO: replace String with AirportEnum and dates...
    suspend fun getFlights(requestFlight: RequestFlight): Array<Service>?
}
