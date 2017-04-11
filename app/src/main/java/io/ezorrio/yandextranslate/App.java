package io.ezorrio.yandextranslate;

import android.app.Application;

import io.ezorrio.yandextranslate.api.ApiHelper;

/**
 * Created by golde on 28.03.2017.
 */

public class App extends Application {
    private static ApiHelper mApiHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        mApiHelper = new ApiHelper();
    }

    public static ApiHelper getApiHelper() {
        return mApiHelper;
    }
}
