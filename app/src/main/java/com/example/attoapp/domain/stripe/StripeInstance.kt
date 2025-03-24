package com.example.attoapp.domain.stripe

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StripeInstance {
    val api: StripeApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-server.com/") // Замени на свой URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeApi::class.java)
    }
}