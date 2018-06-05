package com.shashavs.architecture.components

import android.arch.lifecycle.ViewModel
import com.shashavs.architecture.database.ItemEntity

class DetailViewModel: ViewModel() {
    var item: ItemEntity? = null
}