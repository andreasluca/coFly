package com.andreas.fly.model

data class ResponseFlight(
    private val from: String,
    private val to: String,
    private val departure: String,
    private val arrival: String,
    private val duration: String
)
