package com.coolreecedev.styledpractice.data.zipcode

import com.coolreecedev.styledpractice.data.zipcode.ZipCode
import retrofit2.Response
import retrofit2.http.GET

interface ZipCodeService {

        @GET("/v1/getZipCodes")
        suspend fun getZipCodes(): Response<List<ZipCode>>

}