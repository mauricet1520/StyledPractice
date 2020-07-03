package com.coolreecedev.styledpractice.data.zipcode

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.util.ZIP_CODE_VALIDATOR_URL
import com.coolreecedev.styledpractice.data.database.StyleDatabase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ZipCodeRepository(val app: Application) {

    val zipCodeData = MutableLiveData<List<ZipCode>>()
    val oneZipCodeData = MutableLiveData<ZipCode>()
    val zipCodeDao = StyleDatabase.getDatabase(
        app
    ).zipcodeDao()
    init {

        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
//            val data = zipCodeDao.getAll()
//            if (data.isEmpty()) {
//                callWebService()
//            }else {
//                zipCodeData.postValue(data)
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(app, "Local Data", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }

    @WorkerThread
    suspend fun callWebService() {

        if (networkAvailable()) {
            Log.i(LOG_TAG, "Calling WebService: $ZIP_CODE_VALIDATOR_URL")

            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Remote Data", Toast.LENGTH_SHORT).show()
            }

             val client =  OkHttpClient.Builder()
                .addInterceptor(
                    BasicAuthInterceptor(
                        "Maurice",
                        "Reece"
                    )
                )
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(ZIP_CODE_VALIDATOR_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val service = retrofit.create(ZipCodeService::class.java)

            val serviceData = service.getZipCodes().body() ?: emptyList()
            Log.i(LOG_TAG, "Data: $serviceData")
            zipCodeData.postValue(serviceData)
            zipCodeDao.deleteAll()
            zipCodeDao.insertAll(serviceData)
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

    class BasicAuthInterceptor(username: String, password: String): Interceptor {
        private var credentials: String = Credentials.basic(username, password)

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", credentials).build()

            return chain.proceed(request)
        }
    }


    //Calling the database
    private fun getOneZipCode(zipCode: String) : ZipCode {
        return zipCodeDao.getOne(zipCode)
    }

    fun getOne(zip: String){
        var zipCode : ZipCode

        CoroutineScope(Dispatchers.IO).launch {
            zipCode = getOneZipCode(zip)
            zipCode.let {
                oneZipCodeData.postValue(zipCode)
            }

            withContext(Dispatchers.Main) {
                Log.i(LOG_TAG, "Calling Local Database: $zipCode")
//                Toast.makeText(app, "Calling Database $zipCode", Toast.LENGTH_SHORT).show()
            }
            Log.i(LOG_TAG, "Finish Calling Local Database: $zipCode")
        }
    }
}