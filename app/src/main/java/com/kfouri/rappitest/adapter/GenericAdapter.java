package com.kfouri.rappitest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.activity.MovieDataActivity;
import com.kfouri.rappitest.activity.TvDataActivity;
import com.kfouri.rappitest.model.Movie;
import com.kfouri.rappitest.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenericAdapter extends android.support.v7.widget.RecyclerView.Adapter<GenericAdapter.ViewHolder> {

    private ArrayList<Video> mList = new ArrayList<>();
    private static final String IMAGES_URL = "http://image.tmdb.org/t/p/w500";
    private Context mContext;

    public GenericAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.generic_item,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Video item = mList.get(position);
        viewHolder.imgPoster.setImageBitmap(null);

        Picasso.with(mContext)
                .load(IMAGES_URL + item.getPoster_path())
                .into(viewHolder.imgPoster);

        viewHolder.itemView.setTag(item);

        viewHolder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;

                if (item instanceof Movie) {
                    intent = new Intent(mContext, MovieDataActivity.class);
                } else {
                    intent = new Intent(mContext, TvDataActivity.class);
                }
                intent.putExtra("id", item.getId());
                intent.putExtra("posterPath", item.getPoster_path());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(ArrayList<Video> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;

        ViewHolder(View itemView) {
            super(itemView);
            imgPoster = (ImageView) itemView.findViewById(R.id.image_poster);
        }
    }
}
