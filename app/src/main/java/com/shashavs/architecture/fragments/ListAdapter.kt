package com.shashavs.architecture.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashavs.architecture.R
import com.shashavs.architecture.database.ItemEntity
import kotlinx.android.synthetic.main.fragment_item.view.*

class ListAdapter(private val mValues: List<ItemEntity>, val listener: OnItemListener):
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = mValues.size

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

//            mView.setOnLongClickListener({
//                listener.onItemLongClick(item)
//                true
//            })
        }
    }
}
