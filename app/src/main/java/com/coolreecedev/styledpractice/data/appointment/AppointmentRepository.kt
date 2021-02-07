package com.coolreecedev.styledpractice.data.appointment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.AppointmentDTO
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.util.STRIPE_STYLED_BASE_URL
import com.coolreecedev.styledpractice.util.ZIP_CODE_VALIDATOR_URL
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AppointmentRepository(val app: Application) {

    val timeSlotData = MutableLiveData<List<StaffTimeSlots>>()
    val appointmentViewModel = MutableLiveData<AppointmentDTO>()

    fun getStaffTimeSlots(date: String, serviceName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService(date, serviceName)
        }
    }

    fun createAppointment(request: StyledCustomerAppointmentRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            callAppointmentWebService(request)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        CoroutineScope(Dispatchers.IO).launch {
            updatedAppointmentService(appointment)
        }
    }

    fun getAppointment(appointmentId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getAppointmentWebService(appointmentId)
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

    @WorkerThread
    suspend fun updatedAppointmentService(appointment: Appointment) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")
            Log.i(LOG_TAG, "Updating Appointment")

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(AppointmentService::class.java)

            val response = service.updateAppointment(appointment)


//            Log.i(LOG_TAG, "Appointment Repository response code: ${response.code()}")


        }
    }


    @WorkerThread
    suspend fun callAppointmentWebService(styledCustomerAppointmentRequest: StyledCustomerAppointmentRequest) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")
            Log.i(LOG_TAG, "Request: ${styledCustomerAppointmentRequest.createAppointmentRequest}")
            Log.i(LOG_TAG, "Request: ${styledCustomerAppointmentRequest.createCustomerRequest}")

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(AppointmentService::class.java)

            val response = service.createSetmoreCustomerAppointment(styledCustomerAppointmentRequest)

            val appointmentDTO = response.body()

            val code = response.code()

           Log.i(
               LOG_TAG, "Appointment Repo: ${appointmentDTO?.appointment_id}}")
               Log.i(LOG_TAG, "Response Code: $code}}")
               Log.i(LOG_TAG, "Appointment Repo: $response")

            appointmentViewModel.postValue(appointmentDTO)

        }
    }

    @WorkerThread
    suspend fun getAppointmentWebService(appointmentId: String) {
        if (networkAvailable()) {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(AppointmentService::class.java)

            val appointmentDTO = service.getAppointment(appointmentId).body()

            appointmentViewModel.postValue(appointmentDTO)
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