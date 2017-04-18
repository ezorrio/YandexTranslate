package io.ezorrio.yandextranslate.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by golde on 28.03.2017.
 */

public class LanguageDetectionResult {
    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String lang;

    public void setCode(int code) {
        this.code = code;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getCode() {
        return code;
    }

    public String getLangCode() {
        return lang;
    }
}
