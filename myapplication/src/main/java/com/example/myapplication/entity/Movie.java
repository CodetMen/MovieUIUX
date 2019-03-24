package com.example.myapplication.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.db.DatabaseContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.db.DatabaseContract.getColumnInt;
import static com.example.myapplication.db.DatabaseContract.getColumnString;

/**
 * Created by Administrator on 31/12/2017.
 */

public class Movie implements Parcelable {
    private int id;
    private String judulMovie, detailMovie, dateMovie, imgMovie;

    public Movie(JSONObject object){
        try {
            // ToDo Movie vote still doesnt have field check it in th website
            int id = object.getInt("id");
            String judulMovie = object.getString("title");
            String detailMovie = object.getString("overview");
            String dateMovie = object.getString("release_date");
            String imgMovie = object.getString("poster_path");
            String voteMovie = object.getString("vote_average");

            this.id = id;
            this.judulMovie = judulMovie;
            this.detailMovie = detailMovie;
            this.dateMovie = dateMovie;
            this.imgMovie = imgMovie;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getJudulMovie() {
        return judulMovie;
    }

    public String getDetailMovie() {
        return detailMovie;
    }

    public String getDateMovie() {
        return dateMovie;
    }

    public String getImgMovie() {
        return imgMovie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudulMovie(String judulMovie) {
        this.judulMovie = judulMovie;
    }

    public void setDetailMovie(String detailMovie) {
        this.detailMovie = detailMovie;
    }

    public void setDateMovie(String dateMovie) {
        this.dateMovie = dateMovie;
    }

    public void setImgMovie(String imgMovie) {
        this.imgMovie = imgMovie;
    }

    // parcalabla
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judulMovie);
        dest.writeString(this.detailMovie);
        dest.writeString(this.dateMovie);
        dest.writeString(this.imgMovie);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.judulMovie = in.readString();
        this.detailMovie = in.readString();
        this.dateMovie = in.readString();
        this.imgMovie = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // content provider
    public Movie(){}

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.judulMovie = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.detailMovie = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.dateMovie = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.imgMovie = getColumnString(cursor, DatabaseContract.MovieColumns.IMAGE);
    }
}
