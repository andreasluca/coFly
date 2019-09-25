package com.build38.fly.repository

import android.util.Log
import com.build38.fly.model.RequestFlight
import com.build38.fly.model.ResponseFlight
import com.build38.fly.model.Service
import com.build38.fly.model.amadeus.AmadeusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmadeusRepository(private val amadeusApi: AmadeusShoppingApi): Repository {

    companion object {
        private const val LOG_TAG = "AmadeusRepository"
    }

    override fun getFlights(requestFlight: RequestFlight): Array<Service>? {
        var flights: MutableList<Service>? = mutableListOf()
        amadeusApi
            .getRoundTripFlights(requestFlight.from,
                                 requestFlight.to,
                                 requestFlight.departOn,
                                 requestFlight.returnOn!!)
            .enqueue(object : Callback<AmadeusResponse> {
                override fun onResponse(call: Call<AmadeusResponse>, response: Response<AmadeusResponse>) {
                    Log.d(LOG_TAG, "I AM ALIVE")
                    val resp = response.body()?.data?.get(0)?.offerItems?.get(0)?.price
                    Log.d(LOG_TAG, "Price: $resp")

                    val offers = response.body()?.data?.get(0)?.offerItems
                    if (offers == null) {
                        flights = null
                        return
                    }
                    for (offer in offers) {
                        val amadeusDepartureFlightInfo = offer.services[0].segments[0].flightSegment
                        val departureFlight = ResponseFlight(amadeusDepartureFlightInfo.departure.iataCode,
                                                     amadeusDepartureFlightInfo.arrival.iataCode,
                                                     amadeusDepartureFlightInfo.departure.at,
                                                     amadeusDepartureFlightInfo.arrival.at,
                                                     amadeusDepartureFlightInfo.duration)
                        var returnFlight: ResponseFlight? = null
                        // TODO: sobra, pq es roundtrip
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
                }

                override fun onFailure(call: Call<AmadeusResponse>, t: Throwable) {
                    Log.d(LOG_TAG, ":((((((((((")
                    Log.d(LOG_TAG, t.message?:"Thrown error without message")
                    throw t
                }
            })
        return flights?.toTypedArray()
    }
}
