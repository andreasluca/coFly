package com.andreas.fly.model.amadeus

data class AmadeusFlightEndPoint(
    // airport iata code: enum ? private val iata: AmadeusIataCode,
    val iataCode: String,
    val terminal: String,
    val at: String
)
