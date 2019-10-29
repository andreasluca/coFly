package com.andreas.fly.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFlight(
    val from: String,
    val to: String,
    val departure: String,
    val arrival: String,
    val duration: String
) : Parcelable
