package com.thundercandy.epq.database;

import android.provider.BaseColumns;

public final class CategoryContract {

    public static final class CategoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "tblCategories";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE_CREATED = "date_created";
    }

    public static final class CardEntry implements BaseColumns {

        public static final String TABLE_NAME = "tblCards";

        public static final String _ID = BaseColumns._ID;
        public static final String CATEGORY_ID = CategoryEntry._ID;
        public static final String COLUMN_TERM = "term";
        public static final String COLUMN_DEFINITION = "definition";
        public static final String COLUMN_DATE_CREATED = "date_created";
    }
}
