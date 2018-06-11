package com.shashavs.architecture.components

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.shashavs.architecture.database.AppDatabase
import com.shashavs.architecture.database.ItemEntity

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var data: LiveData<PagedList<ItemEntity>>? = null
    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = AppDatabase.getInstance(application)
    }

    fun getDataList(): LiveData<PagedList<ItemEntity>>? {
        if(data == null) {
            data = LivePagedListBuilder(appDatabase?.userDao()?.getAll()!!, 10).build()
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