package com.build38.fly

import AmadeusSecurityApi
import android.content.Context
import com.build38.fly.repository.AmadeusShoppingApi
import com.build38.fly.repository.authentication.AccessTokenAuthenticator
import com.build38.fly.repository.authentication.AccessTokenProviderImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Injector(private val context: Context) {

    private fun injectAmadeusShoppingRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.AMADEUS_SHOPPING_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(injectAuthenticatorOkHttpClient())
        }.build()
    }

    private fun injectAmadeusSecurityRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.AMADEUS_SECURITY_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    private fun injectAmadeusSecurityApi(): AmadeusSecurityApi {
        return injectAmadeusSecurityRetrofit().create(AmadeusSecurityApi::class.java)
    }

    private fun injectAuthenticatorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(AccessTokenAuthenticator(AccessTokenProviderImpl(injectAmadeusSecurityApi())))
            .build()
    }

    fun injectAmadeusShoppingApi(): AmadeusShoppingApi {
        return injectAmadeusShoppingRetrofit().create(AmadeusShoppingApi::class.java)
    }
}