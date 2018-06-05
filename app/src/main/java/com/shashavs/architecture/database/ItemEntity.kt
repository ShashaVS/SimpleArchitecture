package com.shashavs.architecture.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
class ItemEntity: Serializable {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
    var name: String? = null
    var description: String? = null
}