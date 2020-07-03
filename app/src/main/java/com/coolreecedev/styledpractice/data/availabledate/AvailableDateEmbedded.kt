package com.coolreecedev.styledpractice.data.availabledate

data class AvailableDateEmbedded(
    val availableDates: List<AvailableDate>
)

data class AvailableDateWrapper(
    val _embedded: AvailableDateEmbedded
)