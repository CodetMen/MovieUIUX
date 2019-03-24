package com.jarvsite.movieuiux.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.jarvsite.movieuiux.BuildConfig;
import com.jarvsite.movieuiux.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jarvi on 12/9/2017.
 */

public class SearchLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    private boolean mHasResult = false;
    private  String mJudulFilm;
    private static final String API_KEY = BuildConfig.API_KEY;

    public SearchLoader(final Context context, String mJudulpilm) {
        super(context);
        onContentChanged();
        this.mJudulFilm = mJudulpilm;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if (mHasResult){
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+mJudulFilm;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject pilm = list.getJSONObject(i);
                        Movie movieItems = new Movie(pilm);
                        movieItemses.add(movieItems);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                // do nothing
            }
        });

        return movieItemses;
    }

    private void onReleaseResources(ArrayList<Movie> data) {
        // nothing to do
    }
}