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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val languagesViewModel = ViewModelProviders.of(this).get(LanguagesViewModel::class.java)
        val recyclerView = RecyclerView(requireContext())
        val adapter = LanguageAdapter(requireContext())
        val direction = arguments?.getString(KEY_BUNDLE_DIRECTION)
        languagesViewModel.getAllLanguages().observe(this, Observer { languages: List<AppLanguage> -> adapter.data = languages })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(requireContext(), recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {}

                    override fun onItemClick(view: View?, position: Int) {
                        val data = adapter.data!![position].id
                        when (direction) {
                            DIRECTION_FROM -> AppPrefs.saveDirFrom(requireContext(), data)
                            DIRECTION_TO -> AppPrefs.saveDirTo(requireContext(), data)
                        }
                        Navigation.findNavController(getView()!!).navigate(R.id.action_languageChooseDialogFragment_to_translationFragment)
                    }
                }))
        return recyclerView
    }
}