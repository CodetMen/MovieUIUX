package com.jarvsite.movieuiux.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.IMAGE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.TITLE;
import static com.jarvsite.movieuiux.database.DatabaseContract.TABLE_MOVIE;

/**
 * Created by jarvi on 12/19/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_MOVIE = "dbmovie";
    private static  final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_MOVIE = "CREATE TABLE "+TABLE_MOVIE+" ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TITLE+" TEXT NOT NULL, "+RELEASE_DATE+" TEXT NOT NULL, "+IMAGE+" TEXT NOT NULL, "+OVERVIEW+" TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_MOVIE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MOVIE);
        onCreate(db);
    }
}
