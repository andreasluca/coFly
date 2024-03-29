package com.andreas.fly.repository

import com.andreas.fly.model.amadeus.AmadeusResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AmadeusShoppingApi {
    // TODO: non stop is...
    @GET("flight-offers")
    fun getRoundTripFlights(@Query("origin") origin: String,
                            @Query("destination") destination: String,
                            @Query("departureDate") departureDate: String,
                            @Query("returnDate") returnDate: String,
                            @Query("nonStop") nonStop: Boolean = true): Call<AmadeusResponse>
}
