package com.example.enigmacinma.models;

import android.provider.BaseColumns;

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public  static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SHOW_DT = "show_dt";
        public static final String COLUMN_S_REVIEW = "s_review";
        public static final String COLUMN_R_REVIEW = "r_review";
        public static final String COLUMN_M_REVIEW = "m_review";
        public static final String COLUMN_DESCRIPTION = "description";

    }
}
