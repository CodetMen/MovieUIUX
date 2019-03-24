package com.jarvsite.movieuiux.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jarvsite.movieuiux.Movie;
import com.jarvsite.movieuiux.R;

import java.util.concurrent.ExecutionException;

import static com.jarvsite.movieuiux.database.DatabaseContract.CONTENT_URI;

/**
 * Created by Desktop47 on 09/01/2018.
 */

public class StackRemoteViewsFactory implements  RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    private Cursor list;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }



    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        final Movie mv = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_imagebanner_widget);

        Bitmap bmp = null;
        try{
            bmp = Glide.with(mContext)
                    .load(mv.getImgMovie())
                    .asBitmap()
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorAccent)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }
        catch (InterruptedException | ExecutionException e){
            Log.i("Widget Error", "Error loading data");
        }

        rv.setImageViewBitmap(R.id.iv_item_banner, bmp);

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerAppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.iv_item_banner, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Movie(list);
    }
}