package io.ezorrio.yandextranslate.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.LanguageAdapter;
import io.ezorrio.yandextranslate.model.Bookmark;
import io.ezorrio.yandextranslate.model.History;
import io.ezorrio.yandextranslate.model.Language;
import io.ezorrio.yandextranslate.prefs.AppPrefs;
import io.ezorrio.yandextranslate.provider.AppContentProvider;
import io.ezorrio.yandextranslate.utils.LanguageUtils;


/**
 * Created by golde on 28.03.2017.
 */

public class TranslationFragment extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnTouchListener, View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private ViewGroup mTranslationHolder;
    private TextView mInputLang;
    private TextView mTranslationLang;
    private EditText mInput;
    private TextView mTranslation;
    private Spinner mInputSpinner;
    private Spinner mTranslationSpinner;
    private ImageButton mErase;
    private ImageButton mSwap;
    private ArrayList<Language> mLanguages;

    public static TranslationFragment newInstance() {
        Bundle args = new Bundle();
        TranslationFragment fragment = new TranslationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLanguages = App.getLanguageList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translate, container, false);

        mInputLang = (TextView)  root.findViewById(R.id.input_lang);
        mTranslationLang = (TextView) root.findViewById(R.id.result_lang);
        mTranslationHolder = (ViewGroup) root.findViewById(R.id.primary_translation_holder);
        mTranslation = (TextView) root.findViewById(R.id.result);
        mTranslationSpinner = (Spinner) root.findViewById(R.id.result_header);
        mInputSpinner = (Spinner) root.findViewById(R.id.input_header);

        mInput = (EditText) root.findViewById(R.id.input);
        mErase = (ImageButton) root.findViewById(R.id.delete_input);
        mSwap = (ImageButton) root.findViewById(R.id.swap);
        mInput.addTextChangedListener(this);
        mErase.setOnClickListener(this);
        mSwap.setOnClickListener(this);
        configureSpinners();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_bookmark:
                if (!mInput.getText().toString().isEmpty()) {
                    App.getBookmarkRepository().saveBookmark(
                            new Bookmark(0, mInput.getText().toString().trim(),
                                    LanguageUtils.findKeyByName(mInputLang.getText().toString()),
                                    mTranslation.getText().toString().trim(),
                                    LanguageUtils.findKeyByName(mTranslationLang.getText().toString())));
                    Toast.makeText(getContext(), "Bookmark saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Can\'t save empty bookmark", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureSpinners() {
        if (mInputSpinner.getAdapter() == null || mTranslationSpinner.getAdapter() == null) {
            mLanguages = App.getLanguageRepository().getLanguages();

            LanguageAdapter adapter = new LanguageAdapter(getContext(), mLanguages, true);
            LanguageAdapter adapter1 = new LanguageAdapter(getContext(), (ArrayList<Language>) mLanguages.clone(), false);
            mInputSpinner.setAdapter(adapter);
            mTranslationSpinner.setAdapter(adapter1);
        }
        mInputSpinner.setSelection(AppPrefs.getDir(getActivity())[0]);
        mTranslationSpinner.setSelection(AppPrefs.getDir(getActivity())[1]);
        mInputSpinner.setOnItemSelectedListener(this);
        mTranslationSpinner.setOnItemSelectedListener(this);
        mInputSpinner.setOnTouchListener(this);
        mTranslationSpinner.setOnTouchListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String initialText = s.toString();
        if (initialText.isEmpty()) {
            mTranslation.clearComposingText();
            mTranslationHolder.setVisibility(View.GONE);
            return;
        }

        if (isAutoTranslate()){
            App.getApiHelper().detectAsync(initialText, mInputLang);
        }
        mTranslationHolder.setVisibility(View.VISIBLE);
        translate(initialText);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public boolean isAutoTranslate(){
        return mInputSpinner != null && mInputSpinner.getSelectedItem().equals("Detect language");
    }

    private String getTranslationDir() {
        if (mInputSpinner.getSelectedItem() == null || mTranslationSpinner.getSelectedItem() == null) {
            return null;
        }
        String from = LanguageUtils.findKeyByName(mInputSpinner.getSelectedItem().toString());
        String to = LanguageUtils.findKeyByName(mTranslationSpinner.getSelectedItem().toString());
        return isAutoTranslate() ? to : from + "-" + to;
    }

    private void translate(String data) {
        try {
            App.getApiHelper().translateAsync(data, getTranslationDir(), mTranslation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view == null){
            return;
        }
        switch (parent.getId()){
            case R.id.result_header:
                mTranslationLang.setText(mTranslationSpinner.getSelectedItem().toString());
                break;
            case R.id.input_header:
                mInputLang.setText(mInputSpinner.getSelectedItem().toString());
                break;
        }
        translate(mInput.getText().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideKeyboard();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_input:
                if (mInput.getText().toString().isEmpty()){
                    return;
                }
                App.getHistoryRepository().saveHistory(
                        new History(mInput.getText().toString().trim(),
                                mInputLang.getText().toString(),
                                mTranslation.getText().toString().trim(),
                                mTranslationLang.getText().toString()));
                mInput.getText().clear();
                mTranslationHolder.setVisibility(View.GONE);
                break;
            case R.id.swap:
                if (mInputSpinner.getSelectedItemPosition() == 0){
                    break;
                }
                int temp = mTranslationSpinner.getSelectedItemPosition();
                mTranslationSpinner.setSelection(mInputSpinner.getSelectedItemPosition() - 1);
                mInputSpinner.setSelection(temp + 1);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AppPrefs.saveDir(getActivity(), mInputSpinner.getSelectedItemPosition(), mTranslationSpinner.getSelectedItemPosition());
    }
}
