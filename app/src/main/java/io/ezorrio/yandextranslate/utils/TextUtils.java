package io.ezorrio.yandextranslate.utils;

/**
 * Created by golde on 11.04.2017.
 */

public class TextUtils {
    public static String unescape(String data){
        return data.substring(1, data.length() - 1);
    }

    public static String getLanguageCode(String data){
        if ("Detect language".equals(data)){
            return "auto";
        }
        return data.substring(data.lastIndexOf("(") + 1, data.lastIndexOf(")")).toLowerCase();
    }
}
