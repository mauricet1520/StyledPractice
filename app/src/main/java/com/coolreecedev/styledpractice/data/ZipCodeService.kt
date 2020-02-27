package com.coolreecedev.styledpractice.data

import retrofit2.Response
import retrofit2.http.GET

interface ZipCodeService {

        @GET("/v1/getZipCodes")
        suspend fun getZipCodes(): Response<List<ZipCode>>

}