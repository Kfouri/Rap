package com.kfouri.rappitest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.ui.MovieDataActivity;
import com.kfouri.rappitest.ui.TvDataActivity;
import com.kfouri.rappitest.model.Movie;
import com.kfouri.rappitest.model.Tv;
import com.kfouri.rappitest.model.Video;
import com.kfouri.rappitest.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> implements Filterable {

    private ArrayList<Video> mList = new ArrayList<>();
    private ArrayList<Video> mItemsFiltered = new ArrayList<>();;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final Video item = mItemsFiltered.get(position);
        viewHolder.imgPoster.setImageBitmap(null);

        Picasso.with(mContext.getApplicationContext())
                .load(Constants.IMAGES_URL + item.getPoster_path())
                .into(viewHolder.imgPoster);

        viewHolder.itemView.setTag(item);
        viewHolder.imgPoster.setTransitionName(item.getId().toString());
        viewHolder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (item instanceof Movie) {
                    intent = new Intent(mContext.getApplicationContext(), MovieDataActivity.class);
                } else {
                    intent = new Intent(mContext.getApplicationContext(), TvDataActivity.class);
                }
                intent.putExtra("id", item.getId());
                intent.putExtra("posterPath", item.getPoster_path());

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext, viewHolder.imgPoster, item.getId().toString());

                mContext.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsFiltered.size();
    }

    public void setData(ArrayList<Video> list) {
        mList = list;
        mItemsFiltered = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;

        ViewHolder(View itemView) {
            super(itemView);
            imgPoster = (ImageView) itemView.findViewById(R.id.image_poster);
        }
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Video> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = mList;
                } else {
                    for (Video video : mList) {

                        if ((video instanceof Movie)) {
                            if (((Movie)video).getTitle().toLowerCase().contains(query.toLowerCase())) {
                                filtered.add(video);
                            }
                        } else {
                            if (((Tv)video).getName().toLowerCase().contains(query.toLowerCase())) {
                                filtered.add(video);
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mItemsFiltered = (ArrayList<Video>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
