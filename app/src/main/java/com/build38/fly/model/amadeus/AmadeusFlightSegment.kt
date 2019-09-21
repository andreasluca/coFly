package com.build38.fly.model.amadeus

data class AmadeusFlightSegment(
    private val departure: AmadeusFlightEndPoint,
    private val arrival: AmadeusFlightEndPoint,
    private val carrierCode: String,
    private val duration: String
)
