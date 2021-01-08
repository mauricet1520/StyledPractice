package com.coolreecedev.styledpractice.data.appointment

data class CreateCustomerRequest(
    var key: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var email_id: String? = null,
    var country_code: String? = null,
    var cell_phone: String? = null,
    var work_phone: String? = null,
    var home_phone: String? = null,
    var address: String? = null,
    var city: String? = null,
    var state: String? = null,
    var postal_code: String? = null,
    var image_url: String? = null,
    var comment: String? = null
)
data class AdditionalFields(
    var skype_id: String? = null
)