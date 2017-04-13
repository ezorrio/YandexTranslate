package io.ezorrio.yandextranslate.fragment;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.LanguageAdapter;
import io.ezorrio.yandextranslate.model.Bookmark;
import io.ezorrio.yandextranslate.model.Language;


/**
 * Created by golde on 28.03.2017.
 */

public class TranslationFragment extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnTouchListener {
    private String TAG = getClass().getSimpleName();
    private ViewGroup mPrimaryTranslationCard;
    private TextView mInputLang;
    private TextView mTranslationLang;
    private EditText mTranslateInput;
    private TextView mPrimaryTranslation;
    private Spinner mTranslateInputSpinner;
    private Spinner mPrimaryTranslationSpinner;
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
        mLanguages = App.getLanguageRepository().getLanguages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translate, container, false);

        mInputLang = (TextView)  root.findViewById(R.id.input_lang);
        mTranslationLang = (TextView) root.findViewById(R.id.result_lang);
        mPrimaryTranslationCard = (ViewGroup) root.findViewById(R.id.primary_translation_holder);
        mPrimaryTranslation = (TextView) root.findViewById(R.id.result);
        mPrimaryTranslationSpinner = (Spinner) root.findViewById(R.id.result_header);
        mTranslateInputSpinner = (Spinner) root.findViewById(R.id.input_header);

        mTranslateInput = (EditText) root.findViewById(R.id.input);
        mTranslateInput.addTextChangedListener(this);
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
                if (!mTranslateInput.getText().toString().isEmpty()) {
                    App.getBookmarkRepository().saveBookmark(
                            new Bookmark(0, mTranslateInput.getText().toString(),
                                    "en", mPrimaryTranslation.getText().toString(), "ru"));
                } else {
                    Toast.makeText(getContext(), "Can\'t save empty bookmark", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureSpinners() {
        if (mTranslateInputSpinner.getAdapter() == null || mPrimaryTranslationSpinner.getAdapter() == null) {
            mLanguages = App.getLanguageRepository().getLanguages();
            LanguageAdapter adapter = new LanguageAdapter(getContext(), mLanguages, true);
            LanguageAdapter adapter1 = new LanguageAdapter(getContext(), mLanguages, false);
            mTranslateInputSpinner.setAdapter(adapter);
            mPrimaryTranslationSpinner.setAdapter(adapter1);
        }
        mPrimaryTranslationSpinner.setSelection(17);
        mTranslateInputSpinner.setOnItemSelectedListener(this);
        mPrimaryTranslationSpinner.setOnItemSelectedListener(this);
        mTranslateInputSpinner.setOnTouchListener(this);
        mPrimaryTranslationSpinner.setOnTouchListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String initialText = s.toString();
        if (initialText.isEmpty()) {
            mPrimaryTranslation.clearComposingText();
            mPrimaryTranslationCard.setVisibility(View.GONE);
            return;
        }

        if (isAutoTranslate()){
            App.getApiHelper().detectAsync(getContext(), initialText, mInputLang);
        }
        mPrimaryTranslationCard.setVisibility(View.VISIBLE);
        translate(initialText);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public boolean isAutoTranslate(){
        return mTranslateInputSpinner != null && mTranslateInputSpinner.getSelectedItem().equals("Detect language");
    }

    private String getTranslationDir() {
        if (mTranslateInputSpinner.getSelectedItem() == null || mPrimaryTranslationSpinner.getSelectedItem() == null) {
            return null;
        }
        String from = getLanguageCode(mTranslateInputSpinner.getSelectedItem().toString());
        String to = getLanguageCode(mPrimaryTranslationSpinner.getSelectedItem().toString());
        return isAutoTranslate() ? to : from + "-" + to;
    }

    private void translate(String data) {
        try {
            App.getApiHelper().translateAsync(data, getTranslationDir(), mPrimaryTranslation);
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
                mTranslationLang.setText(mPrimaryTranslationSpinner.getSelectedItem().toString());
                break;
            case R.id.input_header:
                mInputLang.setText(mTranslateInputSpinner.getSelectedItem().toString());
                break;
        }
        translate(mTranslateInput.getText().toString());
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

    public String getLanguageCode(String input){
        for (Language language : mLanguages){
            if (language.getLang().equals(input)){
                return language.getId();
            }
        }
        return null;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("input_lang", mTranslateInputSpinner.getSelectedItemPosition());
//        outState.putInt("out_lang", mPrimaryTranslationSpinner.getSelectedItemPosition());
//        outState.putString("input_data", mTranslateInput.getText().toString());
//        outState.putString("translated_data", mPrimaryTranslation.getText().toString());
//        outState.putParcelableArrayList("languages", mLanguages);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null){
//            mLanguages = savedInstanceState.getParcelableArrayList("languages");
//            mTranslateInputSpinner.setSelection(savedInstanceState.getInt("input_lang"));
//            mPrimaryTranslationSpinner.setSelection(savedInstanceState.getInt("out_lang"));
//            mPrimaryTranslation.setText(savedInstanceState.getString("translated_data"));
//            mTranslateInput.setText(savedInstanceState.getString("input_data"));
//        }
//    }
}
