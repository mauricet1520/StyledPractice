package com.coolreecedev.styledpractice.data.payment

import com.coolreecedev.styledpractice.data.appointment.StaffTimeSlots
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentService {

    @POST("createPaymentIntent")
    suspend fun createPaymentIntent(@Body stripeRequest: StripeRequest): Response<String>
}