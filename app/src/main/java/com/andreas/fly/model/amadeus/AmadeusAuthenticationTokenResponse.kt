package com.andreas.fly.model.amadeus

import com.google.gson.annotations.SerializedName

data class AmadeusAuthenticationTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: String,
    @SerializedName("state") val state: String
)
