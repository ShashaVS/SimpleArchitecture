package com.shashavs.architecture.database

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete

@Dao
abstract class ItemsDao {

    @Query("SELECT * FROM items")
    abstract fun getAll(): DataSource.Factory<Int, ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: ItemEntity): Long

    @Delete
    abstract fun delete(item: ItemEntity)
}