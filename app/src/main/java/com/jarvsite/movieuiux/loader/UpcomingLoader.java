package com.jarvsite.movieuiux.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.jarvsite.movieuiux.BuildConfig;
import com.jarvsite.movieuiux.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jarvi on 12/8/2017.
 */

public class UpcomingLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private boolean hasResult = false;
    private ArrayList<Movie> mData;
    private static String API_KEY = BuildConfig.API_KEY;

    public UpcomingLoader(Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()){
            forceLoad();
        }else if (hasResult){
            deliverResult(mData);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            //call method onReleaseResources
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<Movie> mData) {
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieArrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i = 0; i < list.length(); i++ ){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movies = new Movie(movie);
                        movieArrayList.add(movies);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieArrayList;
    }
}
