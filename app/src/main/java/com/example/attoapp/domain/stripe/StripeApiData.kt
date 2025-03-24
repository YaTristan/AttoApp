package com.example.attoapp.domain.stripe

data class PaymentRequest(
    val amount: Int,
    val currency : String,
)
data class PaymentResponse(val clientService : String)
