package io.ezorrio.yandextranslate.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.io.IOException;

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.db.repos.BookmarksRepository;
import io.ezorrio.yandextranslate.model.Bookmark;
import io.ezorrio.yandextranslate.utils.TextUtils;


/**
 * Created by golde on 28.03.2017.
 */

public class TranslationFragment extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnTouchListener {
    private String TAG = getClass().getSimpleName();
    private EditText mTranslateInput;
    private TextView mPrimaryTranslation;
    private Spinner mTranslateInputSpinner;
    private Spinner mPrimaryTranslationSpinner;
    private SharedPreferences spinnerSaving;

    public static TranslationFragment newInstance() {
        Bundle args = new Bundle();
        TranslationFragment fragment = new TranslationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerSaving = getActivity().getSharedPreferences("spinnerstate", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translate, container, false);

        mPrimaryTranslation = (TextView) root.findViewById(R.id.result);
        mPrimaryTranslationSpinner = (Spinner) root.findViewById(R.id.result_header);
        mTranslateInputSpinner = (Spinner) root.findViewById(R.id.input_header);
        configureSpinners();

        mTranslateInput = (EditText) root.findViewById(R.id.input);
        mTranslateInput.addTextChangedListener(this);
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
                BookmarksRepository.getInstance(getActivity()).saveBookmark(
                        new Bookmark(0, mTranslateInput.getText().toString(),
                                "en", mPrimaryTranslation.getText().toString(), "ru"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureSpinners() {
        if (mTranslateInputSpinner.getAdapter() == null || mPrimaryTranslationSpinner.getAdapter() == null) {
            App.getApiHelper().updateTranslationDir(getActivity(), mTranslateInputSpinner, mPrimaryTranslationSpinner);
        }
        mTranslateInputSpinner.setOnItemSelectedListener(this);
        mPrimaryTranslationSpinner.setOnItemSelectedListener(this);
        mTranslateInputSpinner.setOnTouchListener(this);
        mPrimaryTranslationSpinner.setOnTouchListener(this);
        mTranslateInputSpinner.setSelection(spinnerSaving.getInt("spinner1", 0));
        mPrimaryTranslationSpinner.setSelection(spinnerSaving.getInt("spinner2", 0));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String initialText = s.toString();
        if (initialText.isEmpty()) {
            mPrimaryTranslation.clearComposingText();
            return;
        }
        translate(initialText);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private String getTranslationDir() {
        if (mTranslateInputSpinner.getSelectedItem() == null || mPrimaryTranslationSpinner.getSelectedItem() == null) {
            return null;
        }
        String from = TextUtils.getLanguageCode(mTranslateInputSpinner.getSelectedItem().toString());
        String to = TextUtils.getLanguageCode(mPrimaryTranslationSpinner.getSelectedItem().toString());
        return from.equals("auto") ? to : from + "-" + to;
    }

    private void translate(String data) {
        try {
            App.getApiHelper().translate(data, getTranslationDir(), mPrimaryTranslation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        translate(mTranslateInput.getText().toString());
        SharedPreferences.Editor editor = spinnerSaving.edit();
        editor.putInt("spinner1", mTranslateInputSpinner.getSelectedItemPosition());
        editor.putInt("spinner2", mPrimaryTranslationSpinner.getSelectedItemPosition());
        editor.apply();
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
}
