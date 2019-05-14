package com.kfouri.rappitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.model.Season;
import com.kfouri.rappitest.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeasonAdapter extends android.support.v7.widget.RecyclerView.Adapter<SeasonAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Season> mList = new ArrayList<>();
    private Context mContext;

    public SeasonAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.season_item,
                viewGroup, false);

        return new SeasonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder viewHolder, int position) {

        final Season item = mList.get(position);
        viewHolder.imgPosterSeason.setImageBitmap(null);

        Picasso.with(mContext)
                .load(Constants.IMAGES_URL + item.getPoster_path())
                .into(viewHolder.imgPosterSeason);

        viewHolder.txtName.setText(item.getName());
        viewHolder.txtOverview.setText(item.getOverview());
        viewHolder.txtDate.setText(item.getAir_date());

        viewHolder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(ArrayList<Season> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPosterSeason;
        TextView txtName;
        TextView txtOverview;
        TextView txtDate;

        ViewHolder(View itemView) {
            super(itemView);
            imgPosterSeason = (ImageView) itemView.findViewById(R.id.image_poster_season);
            txtName = (TextView) itemView.findViewById(R.id.season_name);
            txtOverview = (TextView) itemView.findViewById(R.id.season_overview);
            txtDate = (TextView) itemView.findViewById(R.id.season_date);
        }
    }
}
