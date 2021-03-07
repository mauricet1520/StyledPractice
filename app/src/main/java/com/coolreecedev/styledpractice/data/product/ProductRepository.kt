package com.coolreecedev.styledpractice.data.product

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

class ProductRepository(val app: Application) {

    val transactionData = MutableLiveData<Transaction>()

    fun getProductsInTransaction(transaction_number: String) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebServiceGetProductsInTransaction(transaction_number)
        }
    }


    fun saveProductInTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            callWebServiceSaveProductsInTransaction(transaction)
        }
    }

    @WorkerThread
    suspend fun callWebServiceSaveProductsInTransaction(transaction: Transaction) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(ProductService::class.java)

            val serviceData = service.saveProductInTransaction(transaction)

//            transactionData.postValue(serviceData)

        }
    }

    @WorkerThread
    suspend fun callWebServiceGetProductsInTransaction(transaction_number: String) {
        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $STRIPE_STYLED_BASE_URL")
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(STRIPE_STYLED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(ProductService::class.java)

            val serviceData = service.getProductsInTransaction(transaction_number).body()

            transactionData.postValue(serviceData)

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