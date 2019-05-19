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
import io.ezorrio.yandextranslate.adapter.HistoryAdapter
import io.ezorrio.yandextranslate.model.room.AppHistory
import io.ezorrio.yandextranslate.model.view.HistoryViewModel

/**
 * Created by golde on 28.03.2017.
 */

class HistoryFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var mHistoryViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        mAdapter = HistoryAdapter(requireContext())
        mRecyclerView = root.findViewById(R.id.list)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        mHistoryViewModel.getAllHistory().observe(this, Observer { history: List<AppHistory> -> mAdapter.data = history })
    }
}
