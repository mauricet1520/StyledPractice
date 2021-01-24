package com.coolreecedev.styledpractice.data.appointment

import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.AppointmentDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppointmentService {

    @GET("getStaffTimeSlots")
    suspend fun getStaffTimeSlots(
        @Query("date") date: String,
        @Query("service") service: String
    ): Response<List<StaffTimeSlots>>

    @POST("createSetmoreCustomerAppointment")
    suspend fun createSetmoreCustomerAppointment(@Body styledCustomerAppointmentRequest: StyledCustomerAppointmentRequest
    ): Response<AppointmentDTO>

    @POST("/updateAppointmentInFB")
    suspend fun updateAppointment(@Body appointment: Appointment)
}