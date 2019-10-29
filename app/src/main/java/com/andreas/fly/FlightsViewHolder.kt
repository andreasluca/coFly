package com.andreas.fly

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.flights_view_holder.view.*

class FlightsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val depature = itemView.departureTextId
    val depatureTime = itemView.depatureTimeTextId
    val arrival = itemView.arrivalTextId
    val arrivalTime = itemView.arrivalTimeTextId
    val price = itemView.priceTextId
    val duration = itemView.durationTextId
}
