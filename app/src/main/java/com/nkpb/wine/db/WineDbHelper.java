package com.nkpb.wine.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nkpb.wine.db.VarietalContract.Varietal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nk222 on 1/24/2017.
 */

public class WineDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "winemate.db";


    private static final String SQL_CREATE_VARIETAL =
        "CREATE TABLE " + Varietal.TABLE_NAME + " (" +
            Varietal._ID + " INTEGER PRIMARY KEY," +
                Varietal.COLUMN_NAME_CLASSIFICATION + " TEXT, " +
            Varietal.COLUMN_NAME_VARIETAL + " TEXT, " +
            Varietal.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_VARIETAL =
            "DROP TABLE IF EXISTS " + Varietal.TABLE_NAME;

    public WineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("WineDbHelper", "new WineDbHelper()");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("WineDbHelper", "onCreate");
        Log.d("WineDbHelper", SQL_CREATE_VARIETAL);
        db.execSQL(SQL_CREATE_VARIETAL);
        populateVarietals(db);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("WineDbHelper", "onUpdate");
        // discard the data and start over
        db.execSQL(SQL_DELETE_VARIETAL);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("WineDbHelper", "onDowngrade");
        onUpgrade(db, oldVersion, newVersion);
    }

    private void populateVarietals(SQLiteDatabase db) {
        Log.d("WineDbHelper", "populateVarietals");
        String[] varietalTypes = new String[] {
                "White:Chardonnay", "White:Vermentino", "White:Verdelho", "White:Semillion", "White:Riesling",
                "Red:Cabernet","Red:Sangiovese","Red:Franc","Red:Zinfandel","Red:Merlot","Red:Pinot Noir","Red:Shiraz"};
        for (String varietal : varietalTypes) {
            String[] splitValue = varietal.split(":");
            String sql = "INSERT INTO " + Varietal.TABLE_NAME +
                    " (" + Varietal.COLUMN_NAME_CLASSIFICATION +
                    ", " + Varietal.COLUMN_NAME_VARIETAL +
                    ") VALUES ('" + splitValue[0] + "','" + splitValue[1] +"')";
            Log.d("WineDbHelper", sql);
            db.execSQL(sql);
        }
    }

    public List<String> getVarietalsFromDb(String classification) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                VarietalContract.Varietal.COLUMN_NAME_VARIETAL
        };

        // Filter results WHERE "title" = 'My Title'
        String selection =  VarietalContract.Varietal.COLUMN_NAME_CLASSIFICATION + " = ?";
        String[] selectionArgs = { classification };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                VarietalContract.Varietal.COLUMN_NAME_VARIETAL + " DESC";

        Cursor cursor = db.query(
                VarietalContract.Varietal.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List varietals = new ArrayList<>();
        while(cursor.moveToNext()) {
            String varietal = cursor.getString(
                    cursor.getColumnIndexOrThrow(VarietalContract.Varietal.COLUMN_NAME_VARIETAL));
            varietals.add(varietal);
        }
        cursor.close();
        return varietals;
    }

}
