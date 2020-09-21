package com.coolreecedev.styledpractice.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coolreecedev.styledpractice.data.zipcode.ZipCodeRepository

class ZipCodeViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo =
        ZipCodeRepository(app)
    val zipCodeData = dataRepo.zipCodeData
    val oneZipCodeData = dataRepo.oneZipCodeData
    val fireBaseZipCodeData = dataRepo.fireBaseZipCodeData

    fun refreshData() {
        dataRepo.refreshData()
    }

    fun getOneZipCode(zipCode: String) {
        dataRepo.getOne(zipCode)
    }

    fun getFireBaseZipCode(zipCode: String) {
        dataRepo.getFireBaseZip(zipCode)
    }


}
