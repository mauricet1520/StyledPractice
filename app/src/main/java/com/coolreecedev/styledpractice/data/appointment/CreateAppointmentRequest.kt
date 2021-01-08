package com.coolreecedev.styledpractice.data.appointment

data class CreateAppointmentRequest(
    var staff_key: String? = null,
    var service_key: String? = null,
    var customer_key: String? = null,
    var start_time: String? = null,
    var end_time: String? = null,
    var comment: String? = null,
    var label: String,
    var appointmentKey: String? = null
)
