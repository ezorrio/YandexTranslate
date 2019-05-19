package io.ezorrio.yandextranslate.utils;

import java.util.ArrayList;

import io.ezorrio.yandextranslate.model.parcelable.Language;

/**
 * Created by golde on 18.04.2017.
 */

public class LanguageUtils {
    //    private static ArrayList<Language> mList = App.getLanguageList();
    private static ArrayList<Language> mList = null;

    public static Language findByKey(String key) {
        for (Language item : mList) {
            if (item.getId().equals(key)) {
                return item;
            }
        }
        return null;
    }

    public static Language findByName(String name) {
        for (Language item : mList) {
            if (item.getLang().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static String findNameByKey(String key) {
        Language result = findByKey(key);
        if (result != null) {
            return result.getLang();
        } else {
            return null;
        }
    }

    public static String findKeyByName(String name) {
        Language result = findByName(name);
        if (result != null) {
            return result.getId();
        } else {
            return null;
        }
    }
}
