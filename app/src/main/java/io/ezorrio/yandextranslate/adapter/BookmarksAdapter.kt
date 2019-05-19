package io.ezorrio.yandextranslate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.model.room.AppBookmark

/**
 * Created by golde on 10.04.2017.
 */

class BookmarksAdapter(private val mContext: Context) : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {
    var data: List<AppBookmark> = emptyList()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_bookmark, parent, false))
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        holder.lineOne.text = data[position].originalData
        holder.lineTwo.text = data[position].translatedData
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lineOne: TextView = itemView.findViewById(R.id.line_one)
        var lineTwo: TextView = itemView.findViewById(R.id.line_two)

    }
}
