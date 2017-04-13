package io.ezorrio.yandextranslate.utils;

import io.ezorrio.yandextranslate.model.Language;

/**
 * Created by golde on 11.04.2017.
 */

public class TextUtils {
    public static String unescape(String data){
        return data.substring(1, data.length() - 1);
    }
}
