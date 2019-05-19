package io.ezorrio.yandextranslate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.adapter.HistoryAdapter
import io.ezorrio.yandextranslate.model.room.AppHistory
import io.ezorrio.yandextranslate.model.view.HistoryViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by golde on 28.03.2017.
 */

class HistoryFragment : Fragment(), CoroutineScope {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var mClearHistory: Button
    private lateinit var mEmpty: ViewGroup

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    @ObsoleteCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        mEmpty = root.findViewById(R.id.empty_screen)
        mAdapter = HistoryAdapter(requireContext())
        mRecyclerView = root.findViewById(R.id.list)
        mClearHistory = root.findViewById(R.id.clear_history)
        mClearHistory.setOnClickListener {
            launch {
                mHistoryViewModel.clearHistory()
            }
        }
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mHistoryViewModel.getAllHistory().observe(this, Observer { history: List<AppHistory> ->
            run {
                mEmpty.visibility = if (history.isEmpty()) View.VISIBLE else View.GONE
                mAdapter.data = history
            }
        })
        return root
    }
}
