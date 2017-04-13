package io.ezorrio.yandextranslate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import io.ezorrio.yandextranslate.db.columns.BookmarkColumns;
import io.ezorrio.yandextranslate.db.columns.HistoryColumns;
import io.ezorrio.yandextranslate.db.columns.LanguageColumns;

/**
 * Created by golde on 10.04.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "yandex_translate.sqlite";
    private static final int DATABASE_VERSION = 1;

    @NonNull
    public synchronized static DBHelper getInstance(Context context){
        //Log.d(TAG, String.valueOf(context));
        return new DBHelper(context);
    }

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createBookmarks(db);
        createHistory(db);
        createLanguages(db);
        Log.d(TAG, "Created tables!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }

    private void createBookmarks(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS [" + BookmarkColumns.TABLENAME + "] (\n" +
                "  [" + BookmarkColumns._ID + "] INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  [" + BookmarkColumns.ORIGINAL_DATA + "] TEXT, " +
                "  [" + BookmarkColumns.ORIGINAL_LANGUAGE + "] TEXT, " +
                "  [" + BookmarkColumns.TRANSLATED_DATA + "] TEXT, " +
                "  [" + BookmarkColumns.TRANSLATED_LANGUAGE + "] TEXT);";
        db.execSQL(sql);
    }

    private void createHistory(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS [" + HistoryColumns.TABLENAME + "] (\n" +
                "  [" + HistoryColumns._ID + "] INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  [" + HistoryColumns.ORIGINAL_DATA + "] TEXT, " +
                "  [" + HistoryColumns.ORIGINAL_LANGUAGE + "] TEXT, " +
                "  [" + HistoryColumns.TRANSLATED_DATA + "] TEXT, " +
                "  [" + HistoryColumns.TRANSLATED_LANGUAGE + "] TEXT);";
        db.execSQL(sql);
    }
    private void createLanguages(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS [" + LanguageColumns.TABLENAME + "] (\n" +
                "  [" + LanguageColumns._ID + "] TEXT PRIMARY KEY, " +
                "  [" + LanguageColumns.LANG + "] TEXT);";
        db.execSQL(sql);
    }

}
