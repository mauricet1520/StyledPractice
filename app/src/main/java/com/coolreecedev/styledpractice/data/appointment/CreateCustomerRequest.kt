package com.coolreecedev.styledpractice.data.appointment

data class CreateCustomerRequest(
    var key: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var emailId: String? = null,
    var countryCode: String? = null,
    var cellPhone: String? = null,
    var workPhone: String? = null,
    var homePhone: String? = null,
    var address: String? = null,
    var city: String? = null,
    var state: String? = null,
    var postalCode: String? = null,
    var imageUrl: String? = null,
    var comment: String? = null
)
