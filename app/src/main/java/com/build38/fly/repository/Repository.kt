package com.build38.fly.repository

import com.build38.fly.model.RequestFlight
import com.build38.fly.model.Service

interface Repository {

    // TODO: replace String with AirportEnum and dates...
    suspend fun getFlights(requestFlight: RequestFlight): Array<Service>?
}
