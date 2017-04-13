package io.ezorrio.yandextranslate.db.columns;

/**
 * Created by golde on 13.04.2017.
 */

public class LanguageColumns {
    public static final String TABLENAME = "language";

    public static final String _ID = "_id";
    public static final String LANG = "language";


    public static final String FULL_ID = TABLENAME + "." + _ID;
    public static final String FULL_LANG = TABLENAME + "." + LANG;
}
