package io.ezorrio.yandextranslate.db.repos;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.db.columns.BookmarkColumns;
import io.ezorrio.yandextranslate.model.Bookmark;
import io.ezorrio.yandextranslate.provider.AppContentProvider;

/**
 * Created by golde on 10.04.2017.
 */

public class BookmarksRepository extends ContextWrapper {
    public static BookmarksRepository getInstance(Context context){
        return new BookmarksRepository(context);
    }
    public BookmarksRepository(Context base) {
        super(base);
    }

    public ArrayList<Bookmark> getBookmarks() {
        Uri uri = AppContentProvider.getBookmarkContentUri();
        Cursor cursor = getContentResolver().query(uri, null, null, null, BookmarkColumns._ID + " DESC");

        ArrayList<Bookmark> bookmarks = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Bookmark bookmark = transformToObject(cursor);
                bookmarks.add(bookmark);
            }
        }
        return bookmarks;
    }

    private Bookmark transformToObject(@NonNull Cursor cursor) {
        return new Bookmark(cursor.getInt(cursor.getColumnIndex(BookmarkColumns._ID)),
                cursor.getString(cursor.getColumnIndex(BookmarkColumns.ORIGINAL_DATA)),
                cursor.getString(cursor.getColumnIndex(BookmarkColumns.ORIGINAL_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(BookmarkColumns.TRANSLATED_DATA)),
                cursor.getString(cursor.getColumnIndex(BookmarkColumns.TRANSLATED_LANGUAGE)));
    }

    public void saveBookmarkList(ArrayList<Bookmark> data) {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Bookmark post = data.get(i);

            ContentProviderOperation operation = ContentProviderOperation
                    .newInsert(AppContentProvider.getBookmarkContentUri())
                    .withValues(createCv(post))
                    .build();
            operations.add(operation);
        }
        try {
            getContentResolver().applyBatch(AppContentProvider.AUTHORITY, operations);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    public void saveBookmark(Bookmark data) {
        ArrayList<Bookmark> list = new ArrayList<>(1);
        list.add(data);
        saveBookmarkList(list);
    }

    private static ContentValues createCv(Bookmark dto) {
        ContentValues cv = new ContentValues();
        cv.put(BookmarkColumns.ORIGINAL_DATA, dto.getOriginalData());
        cv.put(BookmarkColumns.ORIGINAL_LANGUAGE, dto.getOriginalLang());
        cv.put(BookmarkColumns.TRANSLATED_DATA, dto.getTranslatedData());
        cv.put(BookmarkColumns.TRANSLATED_LANGUAGE, dto.getOriginalLang());
        return cv;
    }

}
