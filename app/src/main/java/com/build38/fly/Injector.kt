package com.build38.fly

import android.content.Context
import com.build38.fly.repository.AmadeusApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Injector(private val context: Context) {

    private fun injectRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.AMADEUS_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(injectOkHttpClient())
        }.build()
    }

    //buildConfigField "String" "API_KEY" "\"QYlNY5V2OWQ4R2E1JfDD76PGHetJvT7N\""
    //            buildConfigField "String" "API_SECRET" "\"2AMwnKeBumrIcuX6\""

    private fun injectOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request = chain.request().newBuilder().apply {
                    addHeader("cache-control", "no-cache")
                    addHeader("Authorization", "Bearer Y8qOVjzBoIV3Jx0aBgaVDkcc3Lli")
                }.build()
                chain.proceed(request)
            }
        }.build()
    }

    fun provideAmadeusApi(): AmadeusApi {
        return injectRetrofit().create(AmadeusApi::class.java)
    }
}