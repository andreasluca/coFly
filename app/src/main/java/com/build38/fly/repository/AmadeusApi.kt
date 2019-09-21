package com.build38.fly.repository

import com.build38.fly.model.amadeus.AmadeusOfferItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AmadeusApi {
    @GET("flight-offers?origin={origin}&destination={destination}&departureDate={departureDate}&returnDate={returnDate}")
    fun getRoundTripFlights(@Path("origin") origin: String,
                            @Path("destination") destination: String,
                            @Path("departureDate") departureDate: String,
                            @Path("returnDate") returnDate: String): Call<List<AmadeusOfferItem>>

}
