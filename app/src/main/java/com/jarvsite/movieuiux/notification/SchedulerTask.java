package com.jarvsite.movieuiux.notification;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by Desktop47 on 11/01/2018.
 */

public class SchedulerTask {

    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodTask(){
        Task task = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(SchedulerService.TASK_MOVIE)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(task);
    }
}
