package io.ezorrio.yandextranslate;

import android.app.Application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import io.ezorrio.yandextranslate.api.ApiHelper;
import io.ezorrio.yandextranslate.db.repos.BookmarksRepository;
import io.ezorrio.yandextranslate.db.repos.HistoryRepository;
import io.ezorrio.yandextranslate.db.repos.LanguageRepository;
import io.ezorrio.yandextranslate.model.Language;

/**
 * Created by golde on 28.03.2017.
 */

public class App extends Application {
    private static ApiHelper mApiHelper;
    private static BookmarksRepository mBookmarkRepo;
    private static HistoryRepository mHistoryRepo;
    private static LanguageRepository mLanguageRepo;
    private static ArrayList<Language> mLanguageList;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiHelper = new ApiHelper();
        mBookmarkRepo = BookmarksRepository.getInstance(this);
        mHistoryRepo = HistoryRepository.getInstance(this);
        mLanguageRepo = LanguageRepository.getInstance(this);
        mApiHelper.getLanguagesAndSave(this);
        mLanguageList = mLanguageRepo.getLanguages();
    }

    public static ApiHelper getApiHelper() {
        return mApiHelper;
    }

    public static BookmarksRepository getBookmarkRepository() {
        return mBookmarkRepo;
    }

    public static HistoryRepository getHistoryRepository() {
        return mHistoryRepo;
    }

    public static LanguageRepository getLanguageRepository() {
        return mLanguageRepo;
    }

    public static ArrayList<Language> getLanguageList() {
        return mLanguageList;
    }
}
