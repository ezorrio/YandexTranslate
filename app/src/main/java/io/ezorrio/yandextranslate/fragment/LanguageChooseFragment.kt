package io.ezorrio.yandextranslate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.adapter.LanguageAdapter
import io.ezorrio.yandextranslate.model.room.AppLanguage
import io.ezorrio.yandextranslate.model.view.LanguagesViewModel
import io.ezorrio.yandextranslate.prefs.AppPrefs
import io.ezorrio.yandextranslate.utils.RecyclerItemClickListener


class LanguageChooseFragment : Fragment() {
    companion object {
        const val KEY_BUNDLE_DIRECTION = "key_bundle_direction"
        const val DIRECTION_FROM = "direction_from"
        const val DIRECTION_TO = "direction_to"
    }

    lateinit var mLanguagesViewModel: LanguagesViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: LanguageAdapter
    lateinit var mDirection: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLanguagesViewModel = ViewModelProviders.of(this).get(LanguagesViewModel::class.java)
        mDirection = arguments?.getString(KEY_BUNDLE_DIRECTION)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_language_choose, container, false)
        mRecyclerView = root.findViewById(R.id.list)
        mAdapter = LanguageAdapter(requireContext())
        mLanguagesViewModel.getAllLanguages().observe(this, Observer { languages: List<AppLanguage> -> mAdapter.data = languages })
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(requireContext(), mRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {}

                    override fun onItemClick(view: View?, position: Int) {
                        val data = mAdapter.data[position].id
                        when (mDirection) {
                            DIRECTION_FROM -> AppPrefs.saveDirFrom(requireContext(), data)
                            DIRECTION_TO -> AppPrefs.saveDirTo(requireContext(), data)
                        }
                        Navigation.findNavController(getView()!!).navigate(R.id.action_languageChooseDialogFragment_to_translationFragment)
                    }
                }))
        return root
    }
}