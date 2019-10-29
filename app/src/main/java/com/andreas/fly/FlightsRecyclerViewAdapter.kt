package com.andreas.fly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreas.fly.model.Service

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
        //
    }
}
