package com.coolreecedev.styledpractice.data.availabledate

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.util.DATE_MANAGER_URL
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.data.database.StyleDatabase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AvailableDateRepository(val app: Application) {

    val availableDateData = MutableLiveData<List<AvailableDate>>()
    val availableDateDao = StyleDatabase.getDatabase(
        app
    ).availableDateDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
//            val data = availableDateDao.getAll()
//            Log.i(LOG_TAG, "Records: ${data.size}")
//            if (data.isEmpty()) {
//                callWebService()
//            } else {
//                availableDateData.postValue(data)
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(app, "Local Data", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }


    @WorkerThread
    suspend fun callWebService() {

        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $DATE_MANAGER_URL")

            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Remote Data", Toast.LENGTH_SHORT).show()
            }
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(DATE_MANAGER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(AvailableDateService::class.java)

            val serviceData = service.getAvailableDates().body() ?: AvailableDateWrapper(
                AvailableDateEmbedded(
                    emptyList<AvailableDate>() as List
                )
            )
            Log.i(LOG_TAG, "Data: $serviceData")
            serviceData._embedded.availableDates.forEach {
                it._id = it.dateTime
            }
            availableDateData.postValue(serviceData._embedded.availableDates)
            availableDateDao.deleteAll()
//            availableDateDao.insertAll(serviceData._embedded.availableDates)
        }

    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
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