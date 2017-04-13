package io.ezorrio.yandextranslate.model;

/**
 * Created by golde on 10.04.2017.
 */

public class History {
    private int id;
    private String originalData;
    private String originalLang;
    private String translatedData;
    private String translatedLang;

    public History(int id, String originalData, String originalLang, String translatedData, String translatedLang) {
        this.id = id;
        this.originalData = originalData;
        this.originalLang = originalLang;
        this.translatedData = translatedData;
        this.translatedLang = translatedLang;
    }

    public History(String originalData, String originalLang, String translatedData, String translatedLang){
        this.originalData = originalData;
        this.originalLang = originalLang;
        this.translatedData = translatedData;
        this.translatedLang = translatedLang;
    }

    public int getId() {
        return id;
    }

    public String getOriginalData() {
        return originalData;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public String getTranslatedData() {
        return translatedData;
    }

    public String getTranslatedLang() {
        return translatedLang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public void setTranslatedData(String translatedData) {
        this.translatedData = translatedData;
    }

    public void setTranslatedLang(String translatedLang) {
        this.translatedLang = translatedLang;
    }
}
