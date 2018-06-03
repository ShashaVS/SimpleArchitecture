package com.shashavs.architecture.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shashavs.architecture.R
import com.shashavs.architecture.database.ItemEntity
import kotlinx.android.synthetic.main.fragment_item.view.*

class ListAdapter(private val mValues: List<ItemEntity>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.id.text = item.uid.toString()
        holder.title.text = item.name
        holder.description.text = item.description
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val id: TextView = mView.item_id
        val title: TextView = mView.item_title
        val description: TextView = mView.item_description
    }
}
