package io.ezorrio.yandextranslate.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by golde on 28.03.2017.
 */

public class TranslationDirs {
    @SerializedName("dirs")
    private List<String> dirs;
    @SerializedName("langs")
    private LinkedHashMap<String, String> langs;

    public LinkedHashMap<String, String> getLangs() {
        return langs;
    }

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public void setLangs(LinkedHashMap<String, String> langs) {
        this.langs = langs;
    }

}
