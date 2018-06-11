package com.shashavs.architecture.fragments

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashavs.architecture.R
import com.shashavs.architecture.database.ItemEntity
import kotlinx.android.synthetic.main.fragment_item.view.*

class ListAdapter(val listener: OnItemListener):
        PagedListAdapter<ItemEntity, ListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemEntity>() {

            override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean =
                    oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) holder.bind(item)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val id = mView.item_id
        private val title = mView.item_title
        private val description = mView.item_description

        fun bind(item: ItemEntity) {
            id.text = item.uid.toString()
            title.text = item.name
            description.text = item.description

            mView.setOnClickListener {
                listener.onItemClick(item)
            }

            mView.setOnLongClickListener({
                listener.onItemLongClick(item)
                true
            })
        }
    }
}
