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

import io.ezorrio.yandextranslate.db.columns.HistoryColumns;
import io.ezorrio.yandextranslate.model.History;
import io.ezorrio.yandextranslate.provider.AppContentProvider;

/**
 * Created by golde on 10.04.2017.
 */

public class HistoryRepository extends ContextWrapper {

    public static HistoryRepository getInstance(Context context){
        return new HistoryRepository(context);
    }

    public HistoryRepository(Context base){
        super(base);
    }

    public ArrayList<History> getHistory() {
        Uri uri = AppContentProvider.getHistoryContentUri();
        Cursor cursor = getContentResolver().query(uri, null, null, null, HistoryColumns._ID + " ASC");

        ArrayList<History> history = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                History bookmark = transformToObject(cursor);
                history.add(bookmark);
            }
        }
        return history;
    }

    private History transformToObject(@NonNull Cursor cursor){
        return new History(cursor.getString(cursor.getColumnIndex(HistoryColumns.ORIGINAL_DATA)),
                cursor.getString(cursor.getColumnIndex(HistoryColumns.ORIGINAL_LANGUAGE)),
                cursor.getString(cursor.getColumnIndex(HistoryColumns.TRANSLATED_DATA)),
                cursor.getString(cursor.getColumnIndex(HistoryColumns.TRANSLATED_LANGUAGE)));
    }

    public void saveHistoryList(ArrayList<History> data) {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            History post = data.get(i);

            ContentProviderOperation operation = ContentProviderOperation
                    .newInsert(AppContentProvider.getHistoryContentUri())
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

    public void saveHistory(History data){
        ArrayList list = new ArrayList<>();
        list.add(data);
        saveHistoryList(list);
    }

    private static ContentValues createCv(History dto) {
        ContentValues cv = new ContentValues();
        cv.put(HistoryColumns.ORIGINAL_DATA, dto.getOriginalData());
        cv.put(HistoryColumns.ORIGINAL_LANGUAGE, dto.getOriginalLang());
        cv.put(HistoryColumns.TRANSLATED_DATA, dto.getTranslatedData());
        cv.put(HistoryColumns.TRANSLATED_LANGUAGE, dto.getOriginalLang());
        return cv;
    }
}
