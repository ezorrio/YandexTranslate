package io.ezorrio.yandextranslate.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.BookmarksAdapter;
import io.ezorrio.yandextranslate.db.repos.BookmarksRepository;
import io.ezorrio.yandextranslate.model.Bookmark;

/**
 * Created by golde on 28.03.2017.
 */

public class BookmarksFragment extends Fragment{
    private static final String TAG = "BookmarksFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<Bookmark> mData;
    private BookmarksAdapter mAdapter;

    public static BookmarksFragment newInstance() {
        Bundle args = new Bundle();
        BookmarksFragment fragment = new BookmarksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = BookmarksRepository.getInstance(getActivity()).getBookmarks();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BookmarksAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mData = BookmarksRepository.getInstance(getContext()).getBookmarks();
        mAdapter = new BookmarksAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
