package com.build38.fly.model

data class Service(
    val departureFlight: ResponseFlight,
    val returnFlight: ResponseFlight? = null,
    val price: Double
)
