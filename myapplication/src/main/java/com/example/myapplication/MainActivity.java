package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.adapter.FavAdapter;
import com.example.myapplication.db.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myapplication.db.DatabaseContract.CONTENT_URI;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.myapplication.db.DatabaseContract.getColumnString;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_app_favorite) ListView lvFavorite;
    FavAdapter adapter;
    private final int LOAD_MOVIES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("App Favorite Movies");

        adapter = new FavAdapter(this, null, true);
        lvFavorite.setAdapter(adapter);
        lvFavorite.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor)adapter.getItem(i);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
        startActivity(intent);
    }
}