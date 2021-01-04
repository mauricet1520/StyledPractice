package com.coolreecedev.styledpractice.data.appointment

data class CreateAppointmentRequest(
    var staffKey: String? = null,
    var serviceKey: String? = null,
    var customerKey: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var comment: String? = null,
    var label: String,
    var appointmentKey: String? = null
)
