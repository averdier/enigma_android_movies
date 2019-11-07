package com.example.enigmacinma.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.enigmacinma.models.MovieContract.*;

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String create_query = "CREATE TABLE " + MovieEntry.TABLE_NAME + "(" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_SHOW_DT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                MovieEntry.COLUMN_M_REVIEW + " FLOAT NOT NULL, " +
                MovieEntry.COLUMN_R_REVIEW + " FLOAT NOT NULL, " +
                MovieEntry.COLUMN_S_REVIEW + " FLOAT NOT NULL, " +
                MovieEntry.COLUMN_DESCRIPTION + " TEXT" +
                ");";

        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
