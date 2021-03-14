package com.coolreecedev.styledpractice.data.checkout

import com.coolreecedev.styledpractice.data.payment.StripeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckoutService {

    @POST("createPaymentIntent")
    suspend fun createPaymentIntent(@Body stripeRequest: StripeRequest): Response<String>
}