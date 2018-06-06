package com.shashavs.architecture.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashavs.architecture.R
import com.shashavs.architecture.components.MainViewModel
import com.shashavs.architecture.database.ItemEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_edit_item.*

class EditDialogFragment: DialogFragment() {

    private var item: ItemEntity? = null
    private var model: MainViewModel? = null

    companion object{
        private val KEY = "item"

        fun instance(item: ItemEntity): DialogFragment {
            val fragment = EditDialogFragment()
            fragment.arguments = Bundle().also { bundle -> bundle.putSerializable(KEY, item) }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        item = arguments?.get(KEY) as ItemEntity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        item_id.text = getString(R.string.item_id, item?.uid.toString())
        edit_name.text = SpannableStringBuilder(item?.name)
        edit_description.text = SpannableStringBuilder(item?.description)

        update_button.setOnClickListener {
            Observable.fromCallable({ insertItem() })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { dismiss() }
                .subscribe() }
    }

    private fun insertItem() {
        item?.name = edit_name.text.toString()
        item?.description = edit_description.text.toString()
        model?.insert(item)
    }
}