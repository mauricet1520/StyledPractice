package com.coolreecedev.styledpractice.data.zipcode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zipcodes")
data class ZipCode(
    val zipCode: String? = null,

    val market: String? = null,

    var id: String? = null,

    @PrimaryKey(autoGenerate = true)
    var zipcode_id: Int? = null
)

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
