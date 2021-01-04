package com.coolreecedev.styledpractice.data.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coolreecedev.styledpractice.data.appointment.AppointmentRepository

class PaymentViewModel(app: Application): AndroidViewModel(app)  {
    private val paymentRepository = PaymentRepository(app)
    val clientSecret = paymentRepository.clientSecret

    fun createPaymentIntent(stripeRequest: StripeRequest) {
        paymentRepository.createPayment(stripeRequest)
    }
}