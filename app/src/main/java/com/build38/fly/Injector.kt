package com.build38.fly

import android.content.Context
import com.build38.fly.repository.AmadeusApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Injector(private val context: Context) {

    private fun injectRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.AMADEUS_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    fun provideAmadeusApi(): AmadeusApi {
        return injectRetrofit().create(AmadeusApi::class.java)
    }
}