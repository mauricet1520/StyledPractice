package com.coolreecedev.styledpractice.data.customer

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CustomerViewModel(app: Application): AndroidViewModel(app) {

    private val customerRepo = CustomerRepository(app)

    val customerAppointmentData = customerRepo.customerAppointmentData

    fun addCustomer(customer: Customer) {
        customerRepo.createCustomer(customer)
    }

    fun getCustomerAppointment(uid: String) {
        customerRepo.getCustomerAppointment(uid)
    }
}