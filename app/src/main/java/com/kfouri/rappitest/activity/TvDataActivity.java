package com.kfouri.rappitest.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.adapter.GenericAdapter;
import com.kfouri.rappitest.adapter.SeasonAdapter;
import com.kfouri.rappitest.model.MovieDataResponse;
import com.kfouri.rappitest.model.TvDataResponse;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDataActivity extends AppCompatActivity {

    //TODO Llevar IMAGES_URL a un lugar mas generico
    private static final String IMAGES_URL = "http://image.tmdb.org/t/p/w500";
    private static final String TAG = "TvDataActivity";

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
    }

    private void initView() {
        posterImageView = (ImageView) findViewById(R.id.image_poster);
        descriptionTextView = (TextView) findViewById(R.id.txtDescription);
        titleTextView = (TextView) findViewById(R.id.txtTitle);
        firstAirDateTextView = (TextView) findViewById(R.id.txtFirstAirDate);
        numberSeasonTitleTextView = (TextView) findViewById(R.id.txtNumberSeasonTitle);
        numberSeasonTextView = (TextView) findViewById(R.id.txtNumberSeason);
        numberEpisodesTitleTextView = (TextView) findViewById(R.id.txtNumberEpisodesTitle);
        numberEpisodesTextView = (TextView) findViewById(R.id.txtNumberEpisodes);
        homepageTextView = (TextView) findViewById(R.id.txtHomepage);
        popularityTextView = (TextView) findViewById(R.id.txtPopularity);
        voteAverageTextView = (TextView) findViewById(R.id.vote_average);

        mSeasonRecycler = findViewById(R.id.seasonList);
        mSeasonRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setPosterImage() {
        posterImageView.setImageBitmap(null);

        Picasso.with(getApplicationContext())
                .load(IMAGES_URL + mPosterPath)
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
