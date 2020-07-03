package com.coolreecedev.styledpractice.data.availabledate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate

@Dao
interface AvailableDateDao {

    @Query("SELECT * from available_dates")
    fun getAll(): List<AvailableDate>

    @Insert
    suspend fun insertAvailableDate(availableDate: AvailableDate)

    @Insert
    suspend fun insertAll(availableDates: List<AvailableDate>)

    @Query("SELECT * FROM available_dates WHERE dateTime = :dateTime")
    fun getOne(dateTime: String): AvailableDate

    @Query("DELETE FROM available_dates")
    suspend fun deleteAll()


}