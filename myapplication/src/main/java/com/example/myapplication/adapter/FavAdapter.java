package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.Movie;
import com.example.myapplication.func.CustomOnItemClickListener;

import static android.provider.BaseColumns._ID;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.IMAGE;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.myapplication.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.myapplication.db.DatabaseContract.getColumnInt;
import static com.example.myapplication.db.DatabaseContract.getColumnString;

/**
 * Created by Administrator on 31/12/2017.
 */

public class FavAdapter extends CursorAdapter {

    public FavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app_cardview, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View itemView, final Context context, final Cursor cursor) {
        ImageView imgPhoto;
        TextView tvTitle, tvDetail, tvDate;

        imgPhoto = itemView.findViewById(R.id.img_movie);
        tvTitle = itemView.findViewById(R.id.tv_judul);
        tvDetail = itemView.findViewById(R.id.tv_detail);
        tvDate = itemView.findViewById(R.id.tv_date);

        tvTitle.setText(getColumnString(cursor, TITLE));
        tvDetail.setText(getColumnString(cursor, OVERVIEW));
        tvDate.setText(getColumnString(cursor, RELEASE_DATE));

        String gambar = getColumnString(cursor, IMAGE);
        Glide.with(context)
                .load(gambar)
                .override(140, 140)
                .into(imgPhoto);
    }



}