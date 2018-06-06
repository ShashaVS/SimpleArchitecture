package com.shashavs.architecture.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashavs.architecture.DetailActivity
import com.shashavs.architecture.R
import com.shashavs.architecture.components.MainViewModel
import com.shashavs.architecture.database.ItemEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), OnItemListener {

    private var adapter: ListAdapter? = null
    private val data: MutableList<ItemEntity> = mutableListOf()
    private var model: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        model?.getDataList()?.observe(this, Observer {
            data.clear()
            if(it != null) {
                data.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ListAdapter(data, this)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                                target: RecyclerView.ViewHolder?): Boolean {
                TODO("not implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder?.adapterPosition

                if(position != null && position != RecyclerView.NO_POSITION) {
                    Observable.fromCallable({ deleteItem(position) })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
//                          .doOnSubscribe { Log.d("TEST", "doOnSubscribe ") }                      //onPreExecute
                            .doOnComplete { Snackbar.make(list, R.string.item_deleted,              //onPostExecute
                                    Snackbar.LENGTH_SHORT).show() }
                            .subscribe()
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(list)
    }

    private fun deleteItem(position: Int) {
        val item = data[position]
        model?.delete(item)
    }

    override fun onItemClick(item: ItemEntity) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("item", item)
        activity?.startActivity(intent)
    }

    override fun onItemLongClick(item: ItemEntity) {
        EditDialogFragment.instance(item).show(childFragmentManager, "EditDialogFragment")
    }

}
