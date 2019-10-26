package com.andreas.fly.model.amadeus

data class AmadeusPrice(
    val total: Double,
    private val totalTaxes: Double
)
