package com.build38.fly.model.amadeus

// OfferItem
data class AmadeusOfferItem(
    // services
    val services: ArrayList<AmadeusService>,
    // price
    val price: AmadeusPrice
)
