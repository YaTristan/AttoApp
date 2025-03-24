package com.example.attoapp.domain.stripe

import retrofit2.http.Body
import retrofit2.http.POST

interface StripeApi {
    @POST("create-payment-intent")
    suspend fun createPaymentIntent(@Body request : PaymentRequest) : PaymentResponse
}

