package com.andreas.fly.model

data class RequestFlight(
    val from: String,
    val to: String,
    val departOn: String,
    val returnOn: String? = null,
    val isRoundTrip: Boolean = false
)
