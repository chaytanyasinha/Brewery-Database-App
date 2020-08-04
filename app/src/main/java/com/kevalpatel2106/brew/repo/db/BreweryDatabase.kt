package com.kevalpatel2106.brew.repo.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kevalpatel2106.brew.repo.dto.BreweryDto

/**
 * Room database for application
 */
@Database(entities = [BreweryDto::class], version = 1, exportSchema = true)
@TypeConverters(DbTypeConverter::class)
abstract class BreweryDatabase : RoomDatabase() {
    internal abstract fun getBreweryDao(): BreweryDao

    companion object {
        private const val DB_NAME = "Brewery.db"

        /**
         * Create application database. Set [inMemory] to true to make in memory database for test.
         */
        fun create(application: Application, inMemory: Boolean = false): BreweryDatabase {
            return if (inMemory) {
                Room.inMemoryDatabaseBuilder(application, BreweryDatabase::class.java)
            } else {
                Room.databaseBuilder(application, BreweryDatabase::class.java, DB_NAME)
            }.build()
        }
    }
}
