package com.coolreecedev.styledpractice.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.data.availabledate.AvailableDateRepository

class AvailableDateViewModel(app: Application): AndroidViewModel(app) {
    private val dataRepo = AvailableDateRepository(app)
    val availableDateData = dataRepo.availableDateData


    val selectedAvailableDate = MutableLiveData<AvailableDate>()
    fun refreshData() {
        dataRepo.refreshData()
    }

}