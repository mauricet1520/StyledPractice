package com.coolreecedev.styledpractice.data.payment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.data.appointment.AppointmentService
import com.coolreecedev.styledpractice.data.appointment.StaffTimeSlots
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.util.STRIPE_STYLED_BASE_URL
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentRepository(val app: Application) {

    val clientSecret = MutableLiveData<String>()

    fun createPayment(stripeRequest: StripeRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService(stripeRequest)
        }
    }


    @WorkerThread
    suspend fun callWebService(stripeRequest: StripeRequest) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(PaymentService::class.java)

            val serviceData = service.createPaymentIntent(stripeRequest).body()


            clientSecret.postValue(serviceData)

        }
    }

    @Suppress("DEPRECATION")
    fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}