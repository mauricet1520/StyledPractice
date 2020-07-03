package com.coolreecedev.styledpractice.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.data.availabledate.AvailableDateDao
import com.coolreecedev.styledpractice.data.zipcode.ZipCode
import com.coolreecedev.styledpractice.data.zipcode.ZipCodeDao
import com.coolreecedev.styledpractice.util.StyledConverter

@Database(entities = [ZipCode::class, AvailableDate::class], version = 3, exportSchema = false)
@TypeConverters(StyledConverter::class)
abstract class StyleDatabase: RoomDatabase() {

    abstract fun zipcodeDao(): ZipCodeDao
    abstract fun availableDateDao(): AvailableDateDao

    companion object {

        @Volatile
        private var INSTANCE: StyleDatabase? = null

        fun getDatabase(context: Context): StyleDatabase {

            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE `available_dates` (`id` TEXT, `availableDateId` INTEGER, `dateTime` TEXT, `dayOfWeek` TEXT, `timeSlot` TEXT," +
                            "PRIMARY KEY(`availableDateId`))")
                }
            }
            if (INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StyleDatabase::class.java,
                        "style.db"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
    }

