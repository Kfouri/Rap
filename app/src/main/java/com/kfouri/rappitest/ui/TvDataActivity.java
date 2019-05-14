package com.kfouri.rappitest.ui;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.adapter.SeasonAdapter;
import com.kfouri.rappitest.model.TvDataResponse;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.kfouri.rappitest.util.Constants;
import com.kfouri.rappitest.util.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDataActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ImageView posterImageView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private TextView firstAirDateTextView;
    private TextView numberSeasonTitleTextView;
    private TextView numberSeasonTextView;
    private TextView numberEpisodesTitleTextView;
    private TextView numberEpisodesTextView;
    private TextView homepageTextView;
    private TextView popularityTextView;
    private TextView voteAverageTextView;
    private TextView connectionErrorTextView;
    private ConstraintLayout generalConstraintLayout;

    private RecyclerView mSeasonRecycler;
    private SeasonAdapter mSeasonAdapter;

    private APIInterface apiInterface;

    private String mPosterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_data);

        Bundle data = this.getIntent().getExtras();
        assert data != null;
        int id = data.getInt("id", 0);
        mPosterPath = data.getString("posterPath");

        apiInterface = APIClient.getClient().create(APIInterface.class);

        initView();

        mSeasonAdapter = new SeasonAdapter(this);
        mSeasonRecycler.setAdapter(mSeasonAdapter);

        posterImageView.setTransitionName(String.valueOf(id));
        setPosterImage();
        getData(id);

        if (!Utils.isNetworkAvailable(this)) {
            connectionErrorTextView.setVisibility(View.VISIBLE);
            generalConstraintLayout.setVisibility(View.GONE);
        }
    }

    private void initView() {
        posterImageView = findViewById(R.id.image_poster);
        descriptionTextView = findViewById(R.id.txtDescription);
        titleTextView = findViewById(R.id.txtTitle);
        firstAirDateTextView = findViewById(R.id.txtFirstAirDate);
        numberSeasonTitleTextView = findViewById(R.id.txtNumberSeasonTitle);
        numberSeasonTextView = findViewById(R.id.txtNumberSeason);
        numberEpisodesTitleTextView = findViewById(R.id.txtNumberEpisodesTitle);
        numberEpisodesTextView = findViewById(R.id.txtNumberEpisodes);
        homepageTextView = findViewById(R.id.txtHomepage);
        popularityTextView = findViewById(R.id.txtPopularity);
        voteAverageTextView = findViewById(R.id.vote_average);
        connectionErrorTextView = findViewById(R.id.connectionError);
        generalConstraintLayout = findViewById(R.id.generalConstraintLayout);

        mSeasonRecycler = findViewById(R.id.seasonList);
        mSeasonRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setPosterImage() {
        posterImageView.setImageBitmap(null);

        Picasso.with(getApplicationContext())
                .load(Constants.IMAGES_URL + mPosterPath)
                .into(posterImageView);
    }

    private void getData(int id) {

        Call<TvDataResponse> call = apiInterface.getTvData(id);

        call.enqueue(new Callback<TvDataResponse>() {
            @Override
            public void onResponse(Call<TvDataResponse> call, Response<TvDataResponse> response) {
                TvDataResponse tvDataResponse = response.body();

                titleTextView.setText(tvDataResponse.getName());
                descriptionTextView.setText(tvDataResponse.getOverview());
                firstAirDateTextView.setText(tvDataResponse.getFirst_air_date());
                numberSeasonTextView.setText(tvDataResponse.getNumber_of_seasons().toString());
                numberEpisodesTextView.setText(tvDataResponse.getNumber_of_episodes().toString());
                popularityTextView.setText(tvDataResponse.getPopularity().toString());
                homepageTextView.setText(tvDataResponse.getHomepage());

                if (tvDataResponse.getNumber_of_seasons() > 1) {
                    numberSeasonTitleTextView.setText(getString(R.string.season_plural));
                } else {
                    numberSeasonTitleTextView.setText(getString(R.string.season_singular));
                }

                if (tvDataResponse.getNumber_of_episodes() > 1) {
                    numberEpisodesTitleTextView.setText(getString(R.string.episode_plural));
                } else {
                    numberEpisodesTitleTextView.setText(getString(R.string.episodes_singular));
                }

                voteAverageTextView.setText(tvDataResponse.getVote_average().toString());

                mSeasonAdapter.setData(tvDataResponse.getSeasons());
            }

            @Override
            public void onFailure(Call<TvDataResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getData()");
            }
        });
    }

}
