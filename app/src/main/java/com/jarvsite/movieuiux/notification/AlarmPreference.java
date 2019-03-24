package com.jarvsite.movieuiux.notification;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Desktop47 on 11/01/2018.
 */

public class AlarmPreference {

    private final String PREFERENCE_NAME = "preference_name";
    private final String REPEATING_TIME = "repeating_time";
    private final String MOVIE_REMINDER = "movie_reminder";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public AlarmPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public String getRepeatingTime() {
        return mSharedPreferences.getString(REPEATING_TIME, null);
    }

    public void setRepeatingTime(String time) {
        editor.putString(REPEATING_TIME, time);
        editor.commit();
    }

    public String getMovieReminder() {
        return mSharedPreferences.getString(MOVIE_REMINDER, null);
    }

    public void setMovieReminder(String message) {
        editor.putString(MOVIE_REMINDER, message);
        editor.commit();
    }
}
