package com.shashavs.architecture.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(ItemEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): ItemsDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase? {
            if (instance == null && context != null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "database.db")
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}