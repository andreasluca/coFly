package com.build38.fly.model

data class Service(
    private val departureFlight: ResponseFlight,
    private val returnFlight: ResponseFlight?,
    private val price: Double
)
