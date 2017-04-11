package io.ezorrio.yandextranslate;

import android.app.Application;

import io.ezorrio.yandextranslate.api.ApiHelper;
import io.ezorrio.yandextranslate.db.repos.BookmarksRepository;
import io.ezorrio.yandextranslate.db.repos.HistoryRepository;

/**
 * Created by golde on 28.03.2017.
 */

public class App extends Application {
    private static ApiHelper mApiHelper;
    private static BookmarksRepository mBookmarkRepo;
    private static HistoryRepository mHistoryRepo;
    @Override
    public void onCreate() {
        super.onCreate();
        mApiHelper = new ApiHelper();
        mBookmarkRepo = BookmarksRepository.getInstance(this);
        mHistoryRepo = HistoryRepository.getInstance(this);
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
}
