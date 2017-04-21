package io.ezorrio.yandextranslate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.model.Language;
import io.ezorrio.yandextranslate.model.api.TranslationDirs;

/**
 * Created by golde on 10.04.2017.
 */

public class LanguageAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private ArrayList<Language> mData;
    public LanguageAdapter(Context context, ArrayList<Language> data, boolean canDetect){
        this.mContext = context;
        this.mData = data;
        if (!canDetect) {
            this.mData.remove(0);
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position).getLang();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_language, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.line_one);
        textView.setText(getItem(position).toString());
        return convertView;
    }
}
