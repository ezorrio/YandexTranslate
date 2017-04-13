package io.ezorrio.yandextranslate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.BookmarksAdapter;
import io.ezorrio.yandextranslate.adapter.HistoryAdapter;
import io.ezorrio.yandextranslate.db.repos.BookmarksRepository;
import io.ezorrio.yandextranslate.db.repos.HistoryRepository;

/**
 * Created by golde on 28.03.2017.
 */

public class HistoryFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;

    public static HistoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList data = App.getHistoryRepository().getHistory();
        mAdapter = new HistoryAdapter(getActivity(), data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }
}
