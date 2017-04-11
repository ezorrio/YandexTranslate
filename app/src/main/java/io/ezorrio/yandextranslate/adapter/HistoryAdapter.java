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
import io.ezorrio.yandextranslate.model.History;

/**
 * Created by golde on 10.04.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context mContext;
    private ArrayList<History> mData;

    public HistoryAdapter(Context context, @NonNull ArrayList<History> data){
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.lineOne.setText(mData.get(position).getOriginalData());
        holder.lineTwo.setText(mData.get(position).getTranslatedData());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView lineOne;
        TextView lineTwo;

        HistoryViewHolder(View itemView) {
            super(itemView);
            lineOne = (TextView) itemView.findViewById(R.id.line_one);
            lineTwo = (TextView) itemView.findViewById(R.id.line_two);
        }
    }
}
