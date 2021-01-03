package com.coolreecedev.styledpractice.data.appointment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.util.STRIPE_STYLED_BASE_URL
import com.coolreecedev.styledpractice.util.ZIP_CODE_VALIDATOR_URL
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentRepository(val app: Application) {

    val timeSlotData = MutableLiveData<List<StaffTimeSlots>>()

    fun getStaffTimeSlots(date: String, serviceName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService(date, serviceName)
        }
    }


    @WorkerThread
    suspend fun callWebService(date: String, serviceName: String) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")


            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(AppointmentService::class.java)

            val serviceData = service.getStaffTimeSlots(date, serviceName).body() ?: emptyList()
            serviceData.forEach {
                Log.i(LOG_TAG, "Staff -Key: ${it.staff.key}")
                Log.i(LOG_TAG, "Staff -FirstName: ${it.staff.first_name}")
                Log.i(LOG_TAG, "sevenToNineTimeSlotsAvailable: ${it.sevenToNineTimeSlotsAvailable}")
                Log.i(LOG_TAG, "eightToTenTimeSlotsAvailable: ${it.eightToTenTimeSlotsAvailable}")

            }
            timeSlotData.postValue(serviceData)

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