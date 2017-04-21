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

import io.ezorrio.yandextranslate.App;
import io.ezorrio.yandextranslate.db.columns.LanguageColumns;
import io.ezorrio.yandextranslate.model.Language;
import io.ezorrio.yandextranslate.provider.AppContentProvider;

/**
 * Created by golde on 13.04.2017.
 */

public class LanguageRepository extends ContextWrapper {

    public static LanguageRepository getInstance(Context context) {
        return new LanguageRepository(context);
    }

    public LanguageRepository(Context base) {
        super(base);
    }

    public ArrayList<Language> getLanguages() {
        Uri uri = AppContentProvider.getLanguageContentUri();
        Cursor cursor = getContentResolver().query(uri, null, null, null, LanguageColumns._ID + " ASC");

        ArrayList<Language> languages = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Language language = transformToObject(cursor);
                languages.add(language);
            }
        }
        return languages;
    }

    private Language transformToObject(@NonNull Cursor cursor) {
        return new Language(cursor.getString(cursor.getColumnIndex(LanguageColumns._ID)),
                cursor.getString(cursor.getColumnIndex(LanguageColumns.LANG)));
    }

    public void saveLanguageList(ArrayList<Language> data) {
        App.setLanguageList(data);
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Language post = data.get(i);

            ContentProviderOperation operation = ContentProviderOperation
                    .newInsert(AppContentProvider.getLanguageContentUri())
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

    public void saveLanguage(Language data) {
        ArrayList<Language> list = new ArrayList<>(1);
        list.add(data);
        saveLanguageList(list);
    }

    private static ContentValues createCv(Language dto) {
        ContentValues cv = new ContentValues();
        cv.put(LanguageColumns._ID, dto.getId());
        cv.put(LanguageColumns.LANG, dto.getLang());
        return cv;
    }
}
