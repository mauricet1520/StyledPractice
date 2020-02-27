package com.coolreecedev.styledpractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.*

@Entity(tableName = "zipcodes")
data class ZipCode(
    val zipCode: String? = null,

    val market: String? = null,

    var id: String? = null,

    @PrimaryKey(autoGenerate = true)
    var zipcode_id: Int? = null
) {
    @ToJson
    fun toJson(zipCode: ZipCode): String {
        return zipCode.toString()
    }

    @FromJson
    fun fromJson(zipCode: String): ZipCode? {

        val moshi = Moshi.Builder().build()
        val adapter : JsonAdapter<ZipCode> = moshi.adapter(ZipCode::class.java)
        return adapter.fromJson(zipCode)
    }
}

class ZipCodeAdapter {

    @ToJson
    fun toJson(zipCode: ZipCode): String {
        return zipCode.toString()
    }
}

//data class Links (
//    @Json(name = "self")
//    var self: Self? = null,
//    @Json(name = "activeZipCode")
//    var activeZipCode: ActiveZipCode? = null
//)
//
//data class ActiveZipCode(
//    @Json(name = "href")
//    var href: String? = null)
//
//
//data class Self (
//    @Json(name = "href")
//    var href: String? = null
//)
