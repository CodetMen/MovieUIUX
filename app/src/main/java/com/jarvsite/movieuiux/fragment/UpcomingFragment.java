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
import com.jarvsite.movieuiux.adapter.CardViewUpcomingAdapter;
import com.jarvsite.movieuiux.loader.UpcomingLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    CardViewUpcomingAdapter adapter;
    RecyclerView rvUpcoming;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_upcoming, container, false);
        setHasOptionsMenu(true);

        getLoaderManager().initLoader(0, null, this);

        rvUpcoming = rootView.findViewById(R.id.rv_upcoming);
        rvUpcoming.setHasFixedSize(true);
        // showRecyclerCardview();
        rvUpcoming.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CardViewUpcomingAdapter(getActivity());

        return rootView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new UpcomingLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapter.setListMovie(data);
        rvUpcoming.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}