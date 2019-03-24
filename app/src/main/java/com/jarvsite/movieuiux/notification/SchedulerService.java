package com.jarvsite.movieuiux.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.jarvsite.movieuiux.BuildConfig;
import com.jarvsite.movieuiux.DetailActivity;
import com.jarvsite.movieuiux.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Desktop47 on 12/01/2018.
 */

public class SchedulerService extends GcmTaskService {

    public static final String TAG = "GetMovie";
    public static String TASK_MOVIE = "task_movie";
    private final String API_KEY = BuildConfig.API_KEY;


    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TASK_MOVIE)){
            getUpcomingMovie();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    public void getUpcomingMovie() {
        Log.i(TAG, "Running getUpcomingMovie method");
        SyncHttpClient httpClient = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY;
        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray parentArray = responseObject.getJSONArray("results");
                    JSONObject contentObject = parentArray.getJSONObject(0);

                    int id = contentObject.getInt("id");
                    String title = contentObject.getString("title");
                    String poster_path = contentObject.getString("poster_path");
                    String overview = contentObject.getString("overview");
                    String release_date = contentObject.getString("release_date");

                    int notifId = 100;
                    showNotification(getApplicationContext(), id, title, poster_path, overview, release_date, notifId);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showNotification(Context applicationContext, int id, String title, String poster_path, String overview, String release_date, int notifId) {
        int idMovie = id;

        Intent notifyIntent = new Intent(getApplicationContext(), DetailActivity.class);

        notifyIntent.putExtra(DetailActivity.EXTRA_TITLE, title);
        notifyIntent.putExtra(DetailActivity.EXTRA_IMAGE, poster_path);
        notifyIntent.putExtra(DetailActivity.EXTRA_DETAIL, overview);
        notifyIntent.putExtra(DetailActivity.EXTRA_DATE, release_date);

        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(applicationContext)
                .setContentTitle("Upcoming Movie")
                .setSmallIcon(R.drawable.ic_theaters_black_24dp)
                .setContentText(title)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        notificationManager.notify(notifId, builder.build());

    }
}