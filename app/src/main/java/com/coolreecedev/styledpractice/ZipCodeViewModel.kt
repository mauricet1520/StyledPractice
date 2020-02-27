package com.coolreecedev.styledpractice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.coolreecedev.styledpractice.data.ZipCode
import com.coolreecedev.styledpractice.data.ZipCodeRepository

class ZipCodeViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo = ZipCodeRepository(app)
    val zipCodeData = dataRepo.zipCodeData
    val oneZipCodeData = dataRepo.oneZipCodeData

    fun refreshData() {
        dataRepo.refreshData()
    }

    fun getOneZipCode(zipCode: String) {
        dataRepo.getOne(zipCode)
    }
}
