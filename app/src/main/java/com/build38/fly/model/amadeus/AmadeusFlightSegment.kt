package com.build38.fly.model.amadeus

data class AmadeusFlightSegment(
    val departure: AmadeusFlightEndPoint,
    val arrival: AmadeusFlightEndPoint,
    val carrierCode: String,
    val duration: String
)
