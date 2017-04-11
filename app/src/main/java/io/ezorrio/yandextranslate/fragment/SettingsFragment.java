package io.ezorrio.yandextranslate.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by golde on 28.03.2017.
 */

public class SettingsFragment extends PreferenceFragment {
    public void saveBunlde(Bundle bundle){
        SharedPreferences prefs = getActivity().getSharedPreferences("translation_bundle", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(bundle.get)
    }
}
