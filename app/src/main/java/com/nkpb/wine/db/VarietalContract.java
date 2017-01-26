package com.nkpb.wine.db;

import android.provider.BaseColumns;

/**
 * Created by nk222 on 1/24/2017.
 */

public final class VarietalContract {
    private VarietalContract() {
    }

    /* Inner class that defines the table contents */
    public static class Varietal implements BaseColumns {
        public static final String TABLE_NAME = "varietal";
        public static final String COLUMN_NAME_CLASSIFICATION = "classification";
        public static final String COLUMN_NAME_VARIETAL = "varietal";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
