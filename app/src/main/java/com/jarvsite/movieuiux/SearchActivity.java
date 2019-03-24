package com.jarvsite.movieuiux;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jarvsite.movieuiux.adapter.SearchAdapter;
import com.jarvsite.movieuiux.loader.SearchLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.listview) RecyclerView listView;

    public final static String EXTRAS_JUDUL = "extra judul" ;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        String mJudul = getIntent().getStringExtra(EXTRAS_JUDUL);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_JUDUL, mJudul);

        getSupportActionBar().setTitle(getResources().getString(R.string.results)+" "+mJudul);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this);

        getLoaderManager().initLoader(0, bundle, this);
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
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        String cariJudul = "";
        if (bundle != null){
            cariJudul = bundle.getString(EXTRAS_JUDUL);
        }
        return new SearchLoader(this, cariJudul);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movieItems) {
        adapter.setmData(movieItems);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setmData(null);
    }

}