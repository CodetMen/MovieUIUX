package com.jarvsite.movieuiux.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

/**
 * Created by jarvi on 12/22/2017.
 */

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor listFavorites;
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public Cursor getListFavorites() {
        return listFavorites;
    }

    public void setListFavorites(Cursor listFavorites) {
        this.listFavorites = listFavorites;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Movie mv = getItem(position);

        final String imageMovie = mv.getImgMovie();
        Glide.with(activity)
                .load(imageMovie)
                .override(160, 285)
                .into(holder.imgPhoto);

        holder.tvTitle.setText(mv.getJudulMovie());
        holder.tvDetail.setText(mv.getDetailMovie());
        holder.tvDate.setText(mv.getDateMovie());


        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_IMAGE, imageMovie);
                intent.putExtra(DetailActivity.EXTRA_TITLE, mv.getJudulMovie());
                intent.putExtra(DetailActivity.EXTRA_DATE, mv.getDateMovie());
                intent.putExtra(DetailActivity.EXTRA_DETAIL, mv.getDetailMovie());
                activity.startActivity(intent);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "this text will be sending soon");
                shareIntent.setType("text/plain");
                activity.startActivity(shareIntent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listFavorites == null) return 0;
        return listFavorites.getCount();
    }

    private Movie getItem(int position){
        if (!listFavorites.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listFavorites);
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
