package io.ezorrio.yandextranslate.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by golde on 13.04.2017.
 */

public class Language implements Parcelable {
    private String id;
    private String lang;

    public Language(String id, String lang){
        this.id = id;
        this.lang = lang;
    }

    public Language(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        this.id = data[0];
        this.lang = data[1];
    }

    public String getId() {
        return id;
    }

    public String getLang() {
        return lang;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id, this.lang});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}
