package com.build38.fly.model.amadeus

data class AmadeusFlightEndPoint(
    // airport iata code: enum ? private val iata: AmadeusIataCode,
    private val iata: String,
    private val terminal: String,
    private val at: String
)
