package com.coolreecedev.styledpractice.data.appointment

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppointmentViewModel(app: Application): AndroidViewModel(app) {

    private val appointmentRepository = AppointmentRepository(app)
    val timeSlotData = appointmentRepository.timeSlotData
    val appointmentData = appointmentRepository.appointmentViewModel

    fun getTimeSlotData(date: String, serviceName: String) {
        appointmentRepository.getStaffTimeSlots(date, serviceName)
    }

    fun createAppointment(styledCustomerAppointmentRequest: StyledCustomerAppointmentRequest) {
        appointmentRepository.createAppointment(styledCustomerAppointmentRequest)
    }
}