package com.coolreecedev.styledpractice.data.customer

import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.AppointmentDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerService {
    @POST("/customer")
    suspend fun addCustomer(@Body customer: Customer
    ): Response<Customer>
}