package com.coolreecedev.styledpractice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface ZipCodeDao {

    @Query("SELECT * from zipcodes")
    fun getAll(): List<ZipCode>

    @Insert
    suspend fun insertZipCode(zipCode: ZipCode)

    @Insert
    suspend fun insertAll(zipCode: List<ZipCode>)

    @Query("SELECT * FROM zipcodes WHERE zipCode = :zipCode")
    fun getOne(zipCode: String): ZipCode

    @Query("DELETE FROM zipcodes")
    suspend fun deleteAll()

}