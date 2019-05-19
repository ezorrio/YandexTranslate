package io.ezorrio.yandextranslate.fragment


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import io.ezorrio.yandextranslate.R
import io.ezorrio.yandextranslate.model.room.AppBookmark
import io.ezorrio.yandextranslate.model.room.AppHistory
import io.ezorrio.yandextranslate.model.room.AppLanguage
import io.ezorrio.yandextranslate.model.view.BookmarkViewModel
import io.ezorrio.yandextranslate.model.view.HistoryViewModel
import io.ezorrio.yandextranslate.model.view.LanguagesViewModel
import io.ezorrio.yandextranslate.model.view.TranslatorViewModel
import io.ezorrio.yandextranslate.prefs.AppPrefs
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by golde on 28.03.2017.
 */

class TranslationFragment : Fragment(), TextWatcher, View.OnClickListener, CoroutineScope, View.OnFocusChangeListener {
    private lateinit var mTranslationHolder: ViewGroup
    private lateinit var mLanguagesHolder: ViewGroup
    private lateinit var mInputLangChoose: Button
    private lateinit var mInputLang: TextView
    private lateinit var mTranslationLangChoose: Button
    private lateinit var mTranslationLang: TextView
    private lateinit var mInput: EditText
    private lateinit var mTranslation: TextView
    private lateinit var mErase: ImageButton
    private lateinit var mSaveFave: ImageButton
    private lateinit var mSwap: ImageButton
    private lateinit var mLanguages: LanguagesViewModel
    private lateinit var mHistory: HistoryViewModel
    private lateinit var mBookmarks: BookmarkViewModel
    private lateinit var mTranslator: TranslatorViewModel
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLanguages = ViewModelProviders.of(this).get(LanguagesViewModel::class.java)
        mHistory = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        mBookmarks = ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
        mTranslator = ViewModelProviders.of(this).get(TranslatorViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_translate, container, false)

        mLanguagesHolder = root.findViewById(R.id.translation_languages_holder)
        mInputLangChoose = root.findViewById(R.id.input_lang_choose)
        mInputLang = root.findViewById(R.id.input_lang)
        mTranslationLangChoose = root.findViewById(R.id.result_lang_choose)
        mTranslationLang = root.findViewById(R.id.result_lang)
        mTranslationHolder = root.findViewById(R.id.primary_translation_holder)
        mTranslation = root.findViewById(R.id.result)

        mInput = root.findViewById(R.id.input)
        mErase = root.findViewById(R.id.delete_input)
        mSaveFave = root.findViewById(R.id.bookmark)
        mSwap = root.findViewById(R.id.swap)
        mInput.addTextChangedListener(this)
        mInput.onFocusChangeListener = this
        mInputLangChoose.setOnClickListener(this)
        mTranslationLangChoose.setOnClickListener(this)
        mErase.setOnClickListener(this)
        mSaveFave.setOnClickListener(this)
        mSwap.setOnClickListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        updateLanguagesData()
        updateTranslationCard()
    }

    private fun updateLanguagesData() {
        mLanguages.getAllLanguages().observe(this, Observer { languages: List<AppLanguage> ->
            run {
                val inputLang = languages.find { appLanguage ->
                    appLanguage.id == AppPrefs.getDir(requireContext())[0]
                }?.lang
                mInputLangChoose.text = inputLang
                mInputLang.text = inputLang
                val translateLang = languages.find { appLanguage ->
                    appLanguage.id == AppPrefs.getDir(requireContext())[1]
                }?.lang
                mTranslationLangChoose.text = translateLang
                mTranslationLang.text = translateLang
            }
        })
    }


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val initialText = s.toString()
        if (initialText.isEmpty()) {
            mTranslation.clearComposingText()
            mTranslationHolder.visibility = View.GONE
            return
        }
    }

    override fun afterTextChanged(s: Editable?) {
        updateTranslationCard()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        mLanguagesHolder.visibility = if (hasFocus) View.GONE else View.VISIBLE
    }

    @ObsoleteCoroutinesApi
    override fun onClick(v: View?) {
        when (v) {
            mErase -> {
                launch {
                    if (mInput.text.toString().trim().isEmpty()) {
                        return@launch
                    }
                    mHistory.saveHistory(AppHistory(originalData = mInput.text.toString().trim(),
                            originalLang = AppPrefs.getDir(context!!)[0]!!,
                            translatedData = mTranslation.text.toString(),
                            translatedLang = AppPrefs.getDir(context!!)[1]!!))
                    mInput.editableText.clear()
                }
                hideKeyboard()
                mInput.clearFocus()
            }

            mSwap -> {
                val inputLang = AppPrefs.getDir(context!!)[0]
                val translateLang = AppPrefs.getDir(context!!)[1]
                AppPrefs.saveDir(context!!, translateLang!!, inputLang!!)
                updateLanguagesData()
                updateTranslationCard()
            }

            mInputLangChoose -> {
                val bundle = Bundle()
                bundle.putString(LanguageChooseFragment.KEY_BUNDLE_DIRECTION, LanguageChooseFragment.DIRECTION_FROM)
                Navigation.findNavController(v).navigate(R.id.action_translationFragment_to_languageChooseDialogFragment, bundle)
            }

            mTranslationLangChoose -> {
                val bundle = Bundle()
                bundle.putString(LanguageChooseFragment.KEY_BUNDLE_DIRECTION, LanguageChooseFragment.DIRECTION_TO)
                Navigation.findNavController(v).navigate(R.id.action_translationFragment_to_languageChooseDialogFragment, bundle)
            }

            mSaveFave -> {
                launch {
                    if (mInput.text.toString().trim().isEmpty()) {
                        return@launch
                    }
                    mBookmarks.saveBookmark(AppBookmark(originalData = mInput.text.toString().trim(),
                            originalLang = AppPrefs.getDir(context!!)[0]!!,
                            translatedData = mTranslation.text.toString(),
                            translatedLang = AppPrefs.getDir(context!!)[1]!!))
                }
                Snackbar.make(v, "Bookmark saved", LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTranslationCard() {
        launch {
            if (mInput.text.isEmpty()) {
                return@launch
            }
            val result = mTranslator.translate(mInput.text.toString().trim(), AppPrefs.getDir(requireContext()))
            mTranslationHolder.visibility = if (result?.text.toString().trim().isNotEmpty()) {
                View.VISIBLE
            } else View.GONE
            mTranslation.text = result?.text?.joinToString()
        }
    }

    private fun hideKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
