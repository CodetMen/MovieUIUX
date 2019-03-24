package com.jarvsite.movieuiux;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jarvsite.movieuiux.database.DatabaseContract;
import com.jarvsite.movieuiux.database.MovieHelper;

import static com.jarvsite.movieuiux.database.DatabaseContract.AUTHORITY;
import static com.jarvsite.movieuiux.database.DatabaseContract.CONTENT_URI;

/**
 * Created by jarvi on 12/19/2017.
 */

public class MovieProvider extends ContentProvider {

    // Integer digunakan sebagai identifier antara select all sama select by id
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Uri matcher untuk mempermudah identifier dengan menggunakan integer
    static {
        // content://com.jarvsite.movieuiux/movie
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);

        // content://com.jarvsite.movieuiux/movie/id
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE+"/#", MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return false;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider(selection, selectionArgs);
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                deleted = movieHelper.delete(selection, selectionArgs);
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
