package com.build38.fly.repository

import com.build38.fly.model.amadeus.AmadeusOfferItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AmadeusApi {
    @GET("flight-offers")
    fun getRoundTripFlights(@Query("origin") origin: String,
                            @Query("destination") destination: String,
                            @Query("departureDate") departureDate: String,
                            @Query("returnDate") returnDate: String): Call<List<AmadeusOfferItem>>

}
