package com.jarvsite.movieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jarvsite.movieuiux.DetailActivity;
import com.jarvsite.movieuiux.Movie;
import com.jarvsite.movieuiux.R;
import com.jarvsite.movieuiux.func.CustomOnItemClickListener;

import java.util.ArrayList;

/**
 * Created by jarvi on 12/9/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Movie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getmData() {
        return mData;
    }

    public void setmData(ArrayList<Movie> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String url_img = "http://image.tmdb.org/t/p/w342";

        final Movie mv = getmData().get(position);

        final String imageMovie = url_img+mv.getImgMovie();
        Glide.with(context)
                .load(imageMovie)
                .override(160, 285)
                .into(holder.imgPhoto);

        holder.tvTitle.setText(mv.getJudulMovie());
        holder.tvDetail.setText(mv.getDetailMovie());
        holder.tvDate.setText(mv.getDateMovie());


        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_IMAGE, imageMovie);
                intent.putExtra(DetailActivity.EXTRA_TITLE, mv.getJudulMovie());
                intent.putExtra(DetailActivity.EXTRA_DATE, mv.getDateMovie());
                intent.putExtra(DetailActivity.EXTRA_DETAIL, mv.getDetailMovie());
                context.startActivity(intent);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "this text will be sending soon");
                shareIntent.setType("text/plain");
                context.startActivity(shareIntent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvTitle, tvDetail, tvDate;
        Button btnDetail, btnShare;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            tvTitle = itemView.findViewById(R.id.tv_judul);
            tvDetail = itemView.findViewById(R.id.tv_detail);
            tvDate = itemView.findViewById(R.id.tv_date);
            btnDetail = itemView.findViewById(R.id.btn_set_detail);
            btnShare = itemView.findViewById(R.id.btn_set_share);
        }
    }
}