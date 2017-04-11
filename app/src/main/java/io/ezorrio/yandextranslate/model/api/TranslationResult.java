package io.ezorrio.yandextranslate.model.api;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by golde on 28.03.2017.
 */

public class TranslationResult {
    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String lang;
    @SerializedName("text")
    private List<String> text;

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public int getCode() {
        return code;
    }

    public List<String> getText() {
        return text;
    }
}
