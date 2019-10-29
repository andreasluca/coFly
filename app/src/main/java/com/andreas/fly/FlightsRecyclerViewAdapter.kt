package com.andreas.fly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreas.fly.model.Service
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FlightsRecyclerViewAdapter(val services: ArrayList<Service>) : RecyclerView.Adapter<FlightsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flights_view_holder, parent, false)
        return FlightsViewHolder(view)
    }

    override fun getItemCount(): Int {
        var numberOfFlights = 0
        for (service in services) {
            numberOfFlights += 1
            service.returnFlight.let { numberOfFlights += 1 }
        }
        return numberOfFlights
    }

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {
        if ((position + 2) % 2 == 0) {
            val servicePos = position/2
            holder.depature.text = services[servicePos].departureFlight.from
            holder.depatureTime.text = convertDate(services[servicePos].departureFlight.departure)
            holder.arrival.text = services[servicePos].departureFlight.to
            holder.arrivalTime.text = convertDate(services[servicePos].departureFlight!!.arrival)

            holder.duration.text = services[servicePos].departureFlight.duration

            // Don't show price only in the return flight
            holder.price.text = "Going flight"
        } else {
            val servicePos = (position - 1)/2
            holder.depature.text = services[servicePos].returnFlight!!.from
            holder.depatureTime.text = convertDate(services[servicePos].returnFlight!!.departure)
            holder.arrival.text = services[servicePos].returnFlight!!.to
            holder.arrivalTime.text = convertDate(services[servicePos].returnFlight!!.arrival)

            holder.duration.text = services[servicePos].returnFlight!!.duration

            // Show price only in the return flight
            holder.price.text = "${services[servicePos].price.toString()} €"
        }
    }

    private fun convertDate(date: String): String {
        val dt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(date)
        val sdf = SimpleDateFormat("hh:mm aa")
        return sdf.format(dt)
    }
}
