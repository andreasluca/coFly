package com.andreas.fly.repository.authentication

import AmadeusSecurityApi
import android.util.Log
import com.andreas.fly.BuildConfig
import com.andreas.fly.Constants
import com.andreas.fly.helpers.SingletonHolder

class AccessTokenProvider private constructor(private val amadeusSecurityApi: AmadeusSecurityApi) :
    AccessTokenProviderInterface {

    companion object : SingletonHolder<AccessTokenProvider, AmadeusSecurityApi>(::AccessTokenProvider) {
        private const val LOG_TAG = "AMADEUS_SECURITY"
    }

    private var token: String? = null

    override fun token(): String? {
        if (token == null) {
            Log.d(LOG_TAG, "Authentication Token is null")
            token = requestNewAuthenticationToken()
        }

        Log.d(LOG_TAG, "Returning Authentication Token: $token")
        return token
    }

    override fun refreshToken(): String? {
        return requestNewAuthenticationToken()
    }

    private fun requestNewAuthenticationToken(): String? {

        Log.d(LOG_TAG, "Requesting new Authentication Token")

        return amadeusSecurityApi.getAuthenticationToken(
            Constants.AMADEUS_AUTH_TOKEN_GRANT_TYPE,
            BuildConfig.AMADEUS_AUTH_TOKEN_CLIENT_ID,
            BuildConfig.AMADEUS_AUTH_TOKEN_CLIENT_SECRET
        ).execute().body()?.accessToken
            ?: throw Exception("$LOG_TAG: Something went bad...")
    }
}
