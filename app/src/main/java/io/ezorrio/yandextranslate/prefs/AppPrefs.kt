package io.ezorrio.yandextranslate.prefs

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by golde on 21.04.2017.
 */

object AppPrefs {
    private val KEY_DIR_FROM = "dir_from"
    private val KEY_DIR_TO = "dir_to"
    private val KEY_IS_INITIAL = "initial_run"

    fun saveDir(context: Context, from: String, to: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_DIR_FROM, from)
                .putString(KEY_DIR_TO, to)
                .apply()
    }

    fun saveDirFrom(context: Context, from: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_DIR_FROM, from)
                .apply()
    }

    fun saveDirTo(context: Context, to: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_DIR_TO, to)
                .apply()
    }

    fun getDir(context: Context): Array<String?> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return arrayOf(preferences.getString(KEY_DIR_FROM, "en"), preferences.getString(KEY_DIR_TO, "ru"))
    }

    fun isInitialRun(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(KEY_IS_INITIAL, true)
    }

    fun setIsInitial(context: Context, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_IS_INITIAL, value)
                .apply()
    }
}
