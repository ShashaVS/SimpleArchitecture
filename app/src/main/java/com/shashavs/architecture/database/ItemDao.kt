package com.shashavs.architecture.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete

@Dao
abstract class ItemsDao {

    @Query("SELECT * FROM items")
    abstract fun getAll(): LiveData<MutableList<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: ItemEntity): Long

    @Delete
    abstract fun delete(item: ItemEntity)
}