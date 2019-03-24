package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.entity.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.db.DatabaseContract.CONTENT_URI;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.IMAGE;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.TITLE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.id_movie_image) ImageView ivMovie;
    @BindView(R.id.id_movie_judul) TextView tvTitle;
    @BindView(R.id.id_movie_detail) TextView tvDetail;
    @BindView(R.id.id_movie_date) TextView tvDate;

    private Movie movieItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Uri uri = getIntent().getData();

        if (uri != null){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){
                if (cursor.moveToFirst()) movieItem = new Movie(cursor);
                cursor.close();
            }else {
                String s = String.valueOf(cursor);
                Log.i("INFO_CURSOR", "CURSOR KOSONG "+s+" "+uri);
            }
        } else{
            Log.i("INFO_URI", "URI KOSONG");
        }

        if (movieItem != null){
            tvTitle.setText(movieItem.getJudulMovie());
            tvDetail.setText(movieItem.getDetailMovie());
            tvDate.setText(movieItem.getDateMovie());

            Glide.with(this)
                    .load(movieItem.getImgMovie())
                    .override(160, 285)
                    .into(ivMovie);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sharing) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "this text will be sending soon");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}