package io.ezorrio.yandextranslate.db.columns;

/**
 * Created by golde on 10.04.2017.
 */

public class BookmarkColumns {
    public static final String TABLENAME = "bookmarks";
    public static final String _ID = "_id";
    public static final String ORIGINAL_DATA = "original_data";
    public static final String ORIGINAL_LANGUAGE = "original_lang";
    public static final String TRANSLATED_DATA = "translated_data";
    public static final String TRANSLATED_LANGUAGE = "translated_lang";

    public static final String FULL_ID = TABLENAME + "." + _ID;
    public static final String FULL_ORIGINAL_DATA = TABLENAME + "." + ORIGINAL_DATA;
    public static final String FULL_ORIGINAL_LANGUAGE = TABLENAME + "." + ORIGINAL_LANGUAGE;
    public static final String FULL_TRANSLATED_DATA = TABLENAME + "." + TRANSLATED_DATA;
    public static final String FULL_TRANSLATED_LANGUAGE = TABLENAME + "." + TRANSLATED_LANGUAGE;
}
