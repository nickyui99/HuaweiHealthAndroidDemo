package com.test2.abc.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test2.abc.R
import com.test2.abc.model.SamplePoint

class HeartRateListAdapter(private val context: Context, private val itemList: List<SamplePoint>) :
    RecyclerView.Adapter<HeartRateListAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txt_heart_rate_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_heart_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textView.text = "${DateTimeUtils.convertMsTimestampToDateTime(currentItem.startTime)} ${DateTimeUtils.convertMsTimestampToDateTime(currentItem.endTime)} ${currentItem.value[0].fieldName} ${currentItem.value[0].floatValue}"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}