package com.andreas.fly.repository

import android.util.Log
import com.andreas.fly.model.RequestFlight
import com.andreas.fly.model.ResponseFlight
import com.andreas.fly.model.Service
import java.lang.Exception

class AmadeusRepository(private val amadeusApi: AmadeusShoppingApi): Repository {

    companion object {
        private const val LOG_TAG = "AmadeusRepository"
    }

    override suspend fun getFlights(requestFlight: RequestFlight): Array<Service>? {
        val flights: MutableList<Service>? = mutableListOf()
        try {
            val response  = amadeusApi
                .getRoundTripFlights(requestFlight.from,
                    requestFlight.to,
                    requestFlight.departOn,
                    requestFlight.returnOn!!).execute()

            Log.d(LOG_TAG, "Start parsing response from the Amadeus API")

            val offers = response.body()?.data?.get(0)?.offerItems ?: return null

            for (offer in offers) {
                val amadeusDepartureFlightInfo = offer.services[0].segments[0].flightSegment
                val departureFlight = ResponseFlight(amadeusDepartureFlightInfo.departure.iataCode,
                    amadeusDepartureFlightInfo.arrival.iataCode,
                    amadeusDepartureFlightInfo.departure.at,
                    amadeusDepartureFlightInfo.arrival.at,
                    amadeusDepartureFlightInfo.duration)
                var returnFlight: ResponseFlight? = null
                if (requestFlight.isRoundTrip) {
                    val amadeusReturnFlightInfo = offer.services[1].segments[0].flightSegment
                    returnFlight = ResponseFlight(amadeusReturnFlightInfo.departure.iataCode,
                        amadeusReturnFlightInfo.arrival.iataCode,
                        amadeusReturnFlightInfo.departure.at,
                        amadeusReturnFlightInfo.arrival.at,
                        amadeusReturnFlightInfo.duration)
                }
                flights?.add(Service(departureFlight, returnFlight, offer.price.total))
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error while accessing Amadeus API: ${e.localizedMessage}")
            return null
        }

        Log.d(LOG_TAG, "Success request to Amadeus API")
        return flights?.toTypedArray()
    }
}
