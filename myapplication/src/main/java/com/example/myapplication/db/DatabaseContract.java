package com.example.myapplication.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.example.myapplication.entity.Movie;

/**
 * Created by Administrator on 31/12/2017.
 */

public class DatabaseContract {
    public static String TABLE_MOVIE = "table_movie";

    public static final class MovieColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String IMAGE = "image";
        public static String OVERVIEW = "overview";
    }

    public static final String AUTHORITY = "com.jarvsite.movieuiux";

    public static final Uri  CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnString){
        return cursor.getString(cursor.getColumnIndex(columnString));
    }

    public static int getColumnInt(Cursor cursor, String columnInt){
        return cursor.getInt(cursor.getColumnIndex(columnInt));
    }

    @Nullable
    public static Long getColumnLong(Cursor cursor, String columnLong){
        return cursor.getLong(cursor.getColumnIndex(columnLong));
    }
}