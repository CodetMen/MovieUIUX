package com.jarvsite.movieuiux;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jarvsite.movieuiux.database.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.jarvsite.movieuiux.database.DatabaseContract.CONTENT_URI;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.IMAGE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.jarvsite.movieuiux.database.DatabaseContract.MovieColumns.TITLE;
import static com.jarvsite.movieuiux.database.DatabaseContract.TABLE_MOVIE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.id_movie_image) ImageView ivMovie;
    @BindView(R.id.id_movie_judul) TextView tvTitle;
    @BindView(R.id.id_movie_detail) TextView tvDetail;
    @BindView(R.id.id_movie_date) TextView tvDate;
    @BindView(R.id.img_set_favorite) ImageView imFavorite;

    public static String EXTRA_IMAGE = "extra image";
    public static String EXTRA_TITLE = "extra title";
    public static String EXTRA_DATE = "extra date";
    public static String EXTRA_DETAIL = "extra detail";

    private Cursor mfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final String imageMv = getIntent().getStringExtra(EXTRA_IMAGE);
        final String titleMv = getIntent().getStringExtra(EXTRA_TITLE);
        final String detailMv = getIntent().getStringExtra(EXTRA_DETAIL);
        final String dateMv = getIntent().getStringExtra(EXTRA_DATE);

        getSupportActionBar().setTitle(titleMv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Glide.with(this).load(imageMv).into(ivMovie);
        tvTitle.setText(titleMv);
        tvDate.setText(dateMv);
        tvDetail.setText(detailMv);

        new LoadFavAsync(titleMv, dateMv, detailMv, imageMv).execute();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
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

    private class LoadFavAsync extends AsyncTask<Void, Void, Cursor> {

        private String judul, tanggal, detail, gambar;

        public LoadFavAsync(String judul, String tanggal, String detail, String gambar) {
            this.judul = judul;
            this.tanggal = tanggal;
            this.detail = detail;
            this.gambar = gambar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            // to do something here

            if(cursor != null && cursor.getCount() > 0){

                // favorite
                imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));

                imFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mSelection = TITLE+" =?";
                        String[] mSelectionArgs = {judul};

                        getContentResolver().delete(CONTENT_URI, mSelection, mSelectionArgs);

                        Toast.makeText(DetailActivity.this, "Anda tidak menyukai "+judul, Toast.LENGTH_LONG).show();
                        imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    }
                });
            }else{
                // unfavorite
                imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));

                imFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues values = new ContentValues();
                        values.put(TITLE, judul);
                        values.put(RELEASE_DATE, tanggal);
                        values.put(IMAGE, gambar);
                        values.put(OVERVIEW, detail);

                        getContentResolver().insert(CONTENT_URI, values);

                        Toast.makeText(DetailActivity.this, "Anda menyukai "+judul, Toast.LENGTH_LONG).show();
                        imFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                    }
                });
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            String[] mProjection = {_ID, TITLE, OVERVIEW, IMAGE, RELEASE_DATE};
            String mSelection = TITLE+" =?";
            String[] mSelectionArgs = {judul};
            return getContentResolver().query(CONTENT_URI, mProjection, mSelection, mSelectionArgs, null);
        }
    }
}
