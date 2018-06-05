package com.shashavs.architecture

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shashavs.architecture.components.DetailViewModel
import com.shashavs.architecture.database.ItemEntity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val model = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        if(model.item == null) {
            val item = intent.extras.get("item") as ItemEntity
            model.item = item
        }

        uid_detail.text = model.item?.uid.toString()
        name_detail.text = model.item?.name
        description_detail.text = model.item?.description
    }
}
