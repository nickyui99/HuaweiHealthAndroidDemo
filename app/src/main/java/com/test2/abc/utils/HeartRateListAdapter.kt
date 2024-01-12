package com.test2.abc.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test2.abc.R
import com.test2.abc.model.Group

class HeartRateListAdapter(private val itemList: List<Group>) : RecyclerView.Adapter<HeartRateListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_heart_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.titleTextView.text = currentItem.startTime.toString()
        holder.descriptionTextView.text = currentItem.sampleSet.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}