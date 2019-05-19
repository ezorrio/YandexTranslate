package io.ezorrio.yandextranslate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.model.room.AppHistory

/**
 * Created by golde on 10.04.2017.
 */

class HistoryAdapter(private val mContext: Context) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    var data: List<AppHistory> = emptyList()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.lineOne.text = data[position].originalData
        holder.lineTwo.text = data[position].translatedData
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lineOne: TextView = itemView.findViewById(R.id.line_one)
        var lineTwo: TextView = itemView.findViewById(R.id.line_two)

    }
}
