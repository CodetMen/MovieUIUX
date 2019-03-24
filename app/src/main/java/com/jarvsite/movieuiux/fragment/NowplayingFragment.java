package com.jarvsite.movieuiux.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jarvsite.movieuiux.Movie;
import com.jarvsite.movieuiux.R;
import com.jarvsite.movieuiux.adapter.CardViewNowplayingAdapter;
import com.jarvsite.movieuiux.loader.NowplayingLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowplayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    CardViewNowplayingAdapter adapter;
    RecyclerView rvNowplaying;

    public NowplayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_nowplaying, container, false);
        setHasOptionsMenu(true);

        getLoaderManager().initLoader(0, null, this);

        rvNowplaying = rootView.findViewById(R.id.rv_nowplaying);
        rvNowplaying.setHasFixedSize(true);
        rvNowplaying.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CardViewNowplayingAdapter(getActivity());


        return rootView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new NowplayingLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapter.setListMovie(data);
        rvNowplaying.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
