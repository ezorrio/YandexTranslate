package io.ezorrio.yandextranslate.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by golde on 10.04.2017.
 */

public class Bookmark implements Parcelable{
    private int id;
    private String originalData;
    private String originalLang;
    private String translatedData;
    private String translatedLang;

    public Bookmark(int id, String originalData, String originalLang, String translatedData, String translatedLang){
        this.id = id;
        this.originalData = originalData;
        this.originalLang = originalLang;
        this.translatedData = translatedData;
        this.translatedLang = translatedLang;
    }

    public Bookmark(Parcel in){
        this.id = in.readInt();
        String[] data = new String[4];
        in.readStringArray(data);
        this.originalData = data[0];
        this.originalLang = data[1];
        this.translatedData = data[2];
        this.translatedLang = data[3];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeStringArray(new String[]{this.originalData, this.originalLang,
                this.translatedData, this.translatedLang});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

}
