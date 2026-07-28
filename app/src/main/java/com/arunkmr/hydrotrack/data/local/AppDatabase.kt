package com.arunkmr.hydrotrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity

@Database(
    entities = [
        WaterLogEntity::class,
        SymptomLogEntity::class,
        UserSettingsEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun waterLogDao(): WaterLogDao
    abstract fun settingsDao(): SettingsDao
    abstract fun symptomLogDao(): SymptomLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hydrotrack_database"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
