package com.shashavs.architecture.components

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.shashavs.architecture.database.AppDatabase
import com.shashavs.architecture.database.ItemEntity

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var data: LiveData<MutableList<ItemEntity>>? = null
    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getInstance(application)
    }

    fun getDataList(): LiveData<MutableList<ItemEntity>>? {
        if(data == null) {
            data = appDatabase?.userDao()?.getAll()
        }
        return data
    }

    fun insert(item: ItemEntity?) {
        if(item != null) appDatabase?.userDao()?.insert(item)
    }

    fun delete(item: ItemEntity) {
        appDatabase?.userDao()?.delete(item)
    }

}