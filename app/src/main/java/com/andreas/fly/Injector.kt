package com.andreas.fly

import AmadeusSecurityApi
import com.andreas.fly.repository.AmadeusRepository
import com.andreas.fly.repository.AmadeusShoppingApi
import com.andreas.fly.repository.Repository
import com.andreas.fly.repository.authentication.AccessTokenAuthenticator
import com.andreas.fly.repository.authentication.AccessTokenProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

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
            .authenticator(AccessTokenAuthenticator(AccessTokenProvider.getInstance(injectAmadeusSecurityApi())))
            .build()
    }

    private fun injectAmadeusShoppingApi(): AmadeusShoppingApi {
        return injectAmadeusShoppingRetrofit().create(AmadeusShoppingApi::class.java)
    }

    private fun injectRepository(): Repository {
        return AmadeusRepository(injectAmadeusShoppingApi())
    }

    fun injectMainPresenter(): MainPresenter {
        return MainPresenter(injectRepository())
    }
}
