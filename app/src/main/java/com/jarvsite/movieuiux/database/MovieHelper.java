package com.jarvsite.movieuiux.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jarvsite.movieuiux.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.IMAGE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.TITLE;
import static com.jarvsite.movieuiux.database.DatabaseContract.TABLE_MOVIE;

/**
 * Created by jarvi on 12/19/2017.
 */

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
        ,null
        ,null
        ,null
        ,null
        ,null, _ID + " DESC"
        ,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0){
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getInt(cursor.getColumnIndexOrThrow(_ID))));
                movie.setJudulMovie(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDateMovie(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setDetailMovie(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setImgMovie(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie){
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movie.getJudulMovie());
        initialValues.put(RELEASE_DATE, movie.getDateMovie());
        initialValues.put(OVERVIEW, movie.getDetailMovie());
        initialValues.put(IMAGE, movie.getImgMovie());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Movie movie){
        ContentValues args = new ContentValues();
        args.put(TITLE, movie.getJudulMovie());
        args.put(RELEASE_DATE, movie.getDateMovie());
        args.put(OVERVIEW, movie.getDetailMovie());
        args.put(IMAGE, movie.getImgMovie());
        return database.update(DATABASE_TABLE, args, _ID+"='"+movie.getId()+"'", null);
    }

    public int delete(String selection, String[] selectionArgs){
        return database.delete(TABLE_MOVIE, selection, selectionArgs);
    }

    /*
    METHOD DI BAWAH INI ADALAH QUERY UNTUK CONTENT PROVIDER
    NILAI BALIK CURSOR
    */

    /**
     * Ambil data dari note berdasarakan parameter id
     * Gunakan method ini untuk ambil data di dalam provider
     * @param id id note yang dicari
     * @return cursor hasil query
     */
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     * @return cursor hasil query
     */
    public Cursor queryProvider(String selection, String[] selectionArgs){
        return database.query(DATABASE_TABLE
                ,null
                , selection
                ,selectionArgs
                ,null
                ,null
                ,_ID + " DESC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    /**
     * Update data dalam database
     * @param id data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    /**
     * Delete data dalam database
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
