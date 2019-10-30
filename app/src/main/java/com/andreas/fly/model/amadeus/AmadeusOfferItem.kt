package com.andreas.fly.model.amadeus

// OfferItem
data class AmadeusOfferItem(
    // services
    val services: ArrayList<AmadeusService>,
    // price
    val price: AmadeusPrice
)
