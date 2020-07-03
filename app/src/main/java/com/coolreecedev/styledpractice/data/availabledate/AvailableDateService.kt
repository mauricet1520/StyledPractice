package com.coolreecedev.styledpractice.data.availabledate

import com.coolreecedev.styledpractice.data.availabledate.AvailableDateWrapper
import retrofit2.Response
import retrofit2.http.GET

interface AvailableDateService {
    @GET("/availableDates")
    suspend fun getAvailableDates(): Response<AvailableDateWrapper>
}