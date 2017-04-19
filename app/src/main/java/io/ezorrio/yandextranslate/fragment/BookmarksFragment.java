package io.ezorrio.yandextranslate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.BookmarksAdapter;
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
        mData = App.getBookmarkRepository().getBookmarks();
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
}
