package com.coolreecedev.styledpractice.data.appointment

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppointmentService {
    @GET("getStaffTimeSlots")
    suspend fun getStaffTimeSlots(@Query("date") date: String, @Query("service") service: String): Response<List<StaffTimeSlots>>
}