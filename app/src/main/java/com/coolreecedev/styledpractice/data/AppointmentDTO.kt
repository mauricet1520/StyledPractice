package com.coolreecedev.styledpractice.data

import com.google.gson.annotations.SerializedName

data class AppointmentDTO (
    var appointment_id: String? = null,

    var street_address: String? = null,
    var city: String? = null,
    var zip: String? = null,
    var state: String? = null,

    var start_time: String? = null,
    var end_time: String? = null,

    var setmore_appointment_key: String? = null,
    var setmore_service_name: String? = null,
    var setmore_label: String? = null,
    var setmore_staff_key: String? = null,
    var setmore_service_key: String? = null,
    var setmore_customer_key: String? = null,
    var stylist_id: String? = null
)