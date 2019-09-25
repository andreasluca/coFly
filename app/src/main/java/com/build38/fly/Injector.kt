package com.build38.fly

import AmadeusSecurityApi
import com.build38.fly.repository.AmadeusRepository
import com.build38.fly.repository.AmadeusShoppingApi
import com.build38.fly.repository.Repository
import com.build38.fly.repository.authentication.AccessTokenAuthenticator
import com.build38.fly.repository.authentication.AccessTokenProvider
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
