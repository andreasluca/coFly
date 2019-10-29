package com.andreas.fly.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFlight(
    private val from: String,
    private val to: String,
    private val departure: String,
    private val arrival: String,
    private val duration: String
) : Parcelable
