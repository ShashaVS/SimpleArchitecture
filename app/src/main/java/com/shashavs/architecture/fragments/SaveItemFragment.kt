package com.shashavs.architecture.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shashavs.architecture.R
import com.shashavs.architecture.components.AppViewModel
import com.shashavs.architecture.database.ItemEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_save_item.*

class SaveItemFragment: Fragment() {

    private var model: AppViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        save_button.setOnClickListener({
            Observable.fromCallable({ insertItem() })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnSubscribe { Log.d("TEST", "doOnSubscribe ") }                            //onPreExecute
                    .doOnComplete { Snackbar.make(edit_container, R.string.item_added,              //onPostExecute
                            Snackbar.LENGTH_SHORT).show() }
                    .subscribe()
        })
    }

    private fun insertItem() {
        val item = ItemEntity()
        item.name = edit_name.text.toString()
        item.description = edit_description.text.toString()
        model?.insert(item)
    }

}
