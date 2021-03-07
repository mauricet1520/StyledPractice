package com.coolreecedev.styledpractice.data.product

import com.coolreecedev.styledpractice.data.appointment.StaffTimeSlots
import com.coolreecedev.styledpractice.data.customer.CustomerDTO
import com.coolreecedev.styledpractice.data.payment.StripeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("/getProductsInTransaction")
    suspend fun getProductsInTransaction(
        @Query("transaction_number") transaction_number: String): Response<Transaction>

    @POST("/saveProductInTransaction")
    suspend fun saveProductInTransaction(@Body transaction: Transaction)
}