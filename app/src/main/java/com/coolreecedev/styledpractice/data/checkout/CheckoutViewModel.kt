package com.coolreecedev.styledpractice.data.checkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coolreecedev.styledpractice.data.appointment.AppointmentRepository
import com.coolreecedev.styledpractice.data.payment.StripeRequest

class CheckoutViewModel(app: Application): AndroidViewModel(app)  {
    private val checkoutRepository = CheckoutRepository(app)
    val clientSecret = checkoutRepository.clientSecret

    fun createPaymentIntent(stripeRequest: StripeRequest) {
        checkoutRepository.createPayment(stripeRequest)
    }
}