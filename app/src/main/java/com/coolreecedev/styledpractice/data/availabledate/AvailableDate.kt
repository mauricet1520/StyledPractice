package com.coolreecedev.styledpractice.data.availabledate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "available_dates")
data class AvailableDate(
    @PrimaryKey(autoGenerate = true)
    var availableDateId: Int? = null,
    var _id: String? = null,
    var timeSlot: ArrayList<String>? = null,
    var dateTime: String? = null,
    val dayOfWeek: String? = null
)