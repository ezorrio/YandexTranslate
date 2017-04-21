package io.ezorrio.yandextranslate.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by golde on 21.04.2017.
 */

public class AppPrefs {
    private static final String KEY_DIR_FROM = "dir_from";
    private static final String KEY_DIR_TO = "dir_to";

    public static void saveDir(Context context, int from, int to) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(KEY_DIR_FROM, from)
                .putInt(KEY_DIR_TO, to)
                .apply();
    }

    public static int[] getDir(Context context){
        int[] result = new int[2];
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        result[0] = preferences.getInt(KEY_DIR_FROM, 0);
        result[1] = preferences.getInt(KEY_DIR_TO, 16);
        return result;
    }
}
