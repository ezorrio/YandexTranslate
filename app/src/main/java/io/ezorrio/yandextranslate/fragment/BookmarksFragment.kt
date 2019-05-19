package io.ezorrio.yandextranslate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.adapter.BookmarksAdapter
import io.ezorrio.yandextranslate.model.room.AppBookmark
import io.ezorrio.yandextranslate.model.view.BookmarkViewModel

/**
 * Created by golde on 28.03.2017.
 */

class BookmarksFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BookmarksAdapter
    private lateinit var mBookmarkViewModel: BookmarkViewModel
    private lateinit var mEmpty: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBookmarkViewModel = ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        mEmpty = root.findViewById(R.id.empty_screen)
        mRecyclerView = root.findViewById(R.id.list)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = BookmarksAdapter(requireContext())
        mRecyclerView.adapter = mAdapter
        mBookmarkViewModel.getAllBookmarks().observe(this, Observer { bookmarks: List<AppBookmark> ->
            run {
                mEmpty.visibility = if (bookmarks.isEmpty()) View.VISIBLE else View.GONE
                mAdapter.data = bookmarks
            }
        })
        return root
    }
}
