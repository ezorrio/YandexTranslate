package io.ezorrio.yandextranslate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.model.Bookmark;

/**
 * Created by golde on 10.04.2017.
 */

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder> {
    private Context mContext;
    private ArrayList<Bookmark> mData;

    public BookmarksAdapter(Context context, @NonNull ArrayList<Bookmark> data){
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public BookmarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookmarksViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_bookmark, parent, false));
    }

    @Override
    public void onBindViewHolder(BookmarksViewHolder holder, int position) {
        holder.lineOne.setText(mData.get(position).getOriginalData());
        holder.lineTwo.setText(mData.get(position).getTranslatedData());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class BookmarksViewHolder extends RecyclerView.ViewHolder {
        TextView lineOne;
        TextView lineTwo;

        BookmarksViewHolder(View itemView) {
            super(itemView);
            lineOne = (TextView) itemView.findViewById(R.id.line_one);
            lineTwo = (TextView) itemView.findViewById(R.id.line_two);
        }
    }
}
