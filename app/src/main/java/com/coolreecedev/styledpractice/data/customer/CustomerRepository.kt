package com.coolreecedev.styledpractice.data.customer

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.util.STRIPE_STYLED_BASE_URL
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CustomerRepository(val app: Application) {

    val customerData = MutableLiveData<Customer>()

    fun createCustomer(request: Customer) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService(request)
        }
    }


    @WorkerThread
    suspend fun callWebService(customer: Customer) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(CustomerService::class.java)

            val serviceData = service.addCustomer(customer).body()

            customerData.postValue(serviceData)

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