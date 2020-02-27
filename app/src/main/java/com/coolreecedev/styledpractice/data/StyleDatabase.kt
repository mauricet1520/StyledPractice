package com.coolreecedev.styledpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZipCode::class], version = 1, exportSchema = false)
abstract class StyleDatabase: RoomDatabase() {

    abstract fun zipcodeDao(): ZipCodeDao

    companion object {

        @Volatile
        private var INSTANCE: StyleDatabase? = null

        fun getDatabase(context: Context): StyleDatabase {
            if (INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StyleDatabase::class.java,
                        "style.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
    }

