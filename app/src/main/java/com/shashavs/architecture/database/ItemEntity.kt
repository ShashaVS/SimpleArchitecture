package com.shashavs.architecture.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items")
class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
    var name: String? = null
    var description: String? = null
}