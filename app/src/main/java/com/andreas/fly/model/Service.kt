package com.andreas.fly.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    val departureFlight: ResponseFlight,
    val returnFlight: ResponseFlight? = null,
    val price: Double
) : Parcelable
