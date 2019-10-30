package com.andreas.fly.model.amadeus

data class AmadeusFlightSegment(
    val departure: AmadeusFlightEndPoint,
    val arrival: AmadeusFlightEndPoint,
    val carrierCode: String,
    val duration: String
)
