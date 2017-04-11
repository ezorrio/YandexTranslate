package io.ezorrio.yandextranslate.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.ezorrio.yandextranslate.db.DBHelper;
import io.ezorrio.yandextranslate.db.columns.BookmarkColumns;
import io.ezorrio.yandextranslate.db.columns.HistoryColumns;

/**
 * Created by golde on 10.04.2017.
 */

public class AppContentProvider extends ContentProvider {
    public static final String TAG = "AppContentProvider";
    private DBHelper mDBHelper;

    private static Map<String, String> sHistoryProjectionMap;
    private static Map<String, String> sBookmarkProjectionMap;

    public static final String AUTHORITY = "io.ezorrio.yandextranslate" + ".providers.AppAuthority";

    static final int URI_HISTORY = 1;
    static final int URI_HISTORY_ID = 2;
    static final int URI_BOOKMARKS = 3;
    static final int URI_BOOKMARKS_ID = 4;

    static final String HISTORY_PATH = "history";
    static final String BOOKMARK_PATH = "bookmark";

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, HISTORY_PATH, URI_HISTORY);
        sUriMatcher.addURI(AUTHORITY, HISTORY_PATH + "/#", URI_HISTORY_ID);
        sUriMatcher.addURI(AUTHORITY, BOOKMARK_PATH, URI_BOOKMARKS);
        sUriMatcher.addURI(AUTHORITY, BOOKMARK_PATH + "/#", URI_BOOKMARKS_ID);
    }
    
    static {
        sBookmarkProjectionMap = new HashMap<>();
        sBookmarkProjectionMap.put(BookmarkColumns._ID, BookmarkColumns.FULL_ID);
        sBookmarkProjectionMap.put(BookmarkColumns.ORIGINAL_DATA, BookmarkColumns.FULL_ORIGINAL_DATA);
        sBookmarkProjectionMap.put(BookmarkColumns.ORIGINAL_LANGUAGE, BookmarkColumns.FULL_ORIGINAL_LANGUAGE);
        sBookmarkProjectionMap.put(BookmarkColumns.TRANSLATED_DATA, BookmarkColumns.FULL_TRANSLATED_DATA);
        sBookmarkProjectionMap.put(BookmarkColumns.TRANSLATED_LANGUAGE, BookmarkColumns.FULL_TRANSLATED_LANGUAGE);
        
        
        sHistoryProjectionMap = new HashMap<>();
        sHistoryProjectionMap.put(HistoryColumns._ID, HistoryColumns.FULL_ID);
        sHistoryProjectionMap.put(HistoryColumns.ORIGINAL_DATA, HistoryColumns.FULL_ORIGINAL_DATA);
        sHistoryProjectionMap.put(HistoryColumns.ORIGINAL_LANGUAGE, HistoryColumns.FULL_ORIGINAL_LANGUAGE);
        sHistoryProjectionMap.put(HistoryColumns.TRANSLATED_DATA, HistoryColumns.FULL_TRANSLATED_DATA);
        sHistoryProjectionMap.put(HistoryColumns.TRANSLATED_LANGUAGE, HistoryColumns.FULL_TRANSLATED_LANGUAGE);
    }

    private static final Uri HISTORY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + HISTORY_PATH);
    static final String HISTORY_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + HISTORY_PATH;
    static final String HISTORY_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + HISTORY_PATH;

    private static final Uri BOOKMARK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BOOKMARK_PATH);
    static final String BOOKMARK_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + BOOKMARK_PATH;
    static final String BOOKMARK_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + BOOKMARK_PATH;

    public static Uri getBookmarkContentUri(){
        return BOOKMARK_CONTENT_URI;
    }

    public static Uri getHistoryContentUri(){
        return HISTORY_CONTENT_URI;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate! Context: " + getContext());
        mDBHelper = DBHelper.getInstance(getContext());
        Log.d(TAG, String.valueOf(mDBHelper));
        return true;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) {
        ContentProviderResult[] result = new ContentProviderResult[operations.size()];
        int i = 0;
        Log.d(TAG, "Apply batch: " + String.valueOf(mDBHelper));
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ContentProviderOperation operation : operations) {
                result[i++] = operation.apply(this, result, i);
            }

            db.setTransactionSuccessful();
        } catch (OperationApplicationException e) {
            Log.d("DATABASE", "batch failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }

        return result;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case URI_BOOKMARKS:
                _QB.setTables(BookmarkColumns.TABLENAME);
                _QB.setProjectionMap(sBookmarkProjectionMap);
                break;

            case URI_BOOKMARKS_ID:
                _QB.setTables(BookmarkColumns.TABLENAME);
                _QB.setProjectionMap(sBookmarkProjectionMap);
                _QB.appendWhere(BookmarkColumns.FULL_ID + "=" + uri.getPathSegments().get(1));
                break;
            case URI_HISTORY:
                _QB.setTables(HistoryColumns.TABLENAME);
                _QB.setProjectionMap(sHistoryProjectionMap);
                break;

            case URI_HISTORY_ID:
                _QB.setTables(HistoryColumns.TABLENAME);
                _QB.setProjectionMap(sHistoryProjectionMap);
                _QB.appendWhere(HistoryColumns.FULL_ID + "=" + uri.getPathSegments().get(1));
                break;
        }

        // Get the database and run the query
        SQLiteDatabase _DB = mDBHelper.getReadableDatabase();
        Cursor _Result = _QB.query(_DB, projection, selection, selectionArgs, null, null, null);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        if (getContext() != null) {
            _Result.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return _Result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case URI_BOOKMARKS:
                return BOOKMARK_CONTENT_TYPE;
            case URI_BOOKMARKS_ID:
                return BOOKMARK_CONTENT_ITEM_TYPE;
            case URI_HISTORY:
                return HISTORY_CONTENT_TYPE;
            case URI_HISTORY_ID:
                return HISTORY_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long rowId;
        Uri result;
        int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case URI_BOOKMARKS:
                rowId = db.insert(BookmarkColumns.TABLENAME, null, values);
                result = ContentUris.withAppendedId(BOOKMARK_CONTENT_URI, rowId);
                break;
            case URI_HISTORY:
                rowId = db.insert(HistoryColumns.TABLENAME, null, values);
                result = ContentUris.withAppendedId(HISTORY_CONTENT_URI, rowId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        safeNotifyChange(result);
        return result;
    }

    private void safeNotifyChange(Uri uri) {
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int matchUri = sUriMatcher.match(uri);
        String tbName;
        switch (matchUri) {
            case URI_BOOKMARKS:
                tbName = BookmarkColumns.TABLENAME;
                break;
            case URI_BOOKMARKS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = BookmarkColumns._ID + " = " + id;
                } else {
                    selection = selection + " AND " + BookmarkColumns._ID + " = " + id;
                }
                tbName = BookmarkColumns.TABLENAME;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int cnt = db.delete(tbName, selection, selectionArgs);

        safeNotifyChange(uri);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
