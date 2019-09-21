package com.build38.fly.model.amadeus

// OfferItem
data class AmadeusOfferItem(
    // services
    private val services: ArrayList<AmadeusService>,
    // price
    private val price: AmadeusPrice
)
