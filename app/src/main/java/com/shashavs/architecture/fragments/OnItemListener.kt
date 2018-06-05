package com.shashavs.architecture.fragments

import com.shashavs.architecture.database.ItemEntity

interface OnItemListener {
    fun onItemClick(item: ItemEntity)
    fun onItemLongClick(item: ItemEntity)
}