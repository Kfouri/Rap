package com.kfouri.rappitest.ui;

import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kfouri.rappitest.R;
import com.kfouri.rappitest.model.MovieDataResponse;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.kfouri.rappitest.util.Constants;
import com.kfouri.rappitest.util.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataActivity extends YouTubeBaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ImageView posterImageView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView adultTextView;
    private TextView popularityTextView;
    private TextView connectionErrorTextView;
    private ConstraintLayout generalConstraintLayout;
    private YouTubePlayerView mYoutubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private String mVideoId;

    private APIInterface apiInterface;

    private String mPosterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);

        Bundle data = this.getIntent().getExtras();
        assert data != null;
        int id = data.getInt("id", 0);
        mPosterPath = data.getString("posterPath");

        apiInterface = APIClient.getClient().create(APIInterface.class);

        initView();

        posterImageView.setTransitionName(String.valueOf(id));
        setPosterImage();
        getData(id);

        if (!Utils.isNetworkAvailable(this)) {
            connectionErrorTextView.setVisibility(View.VISIBLE);
            generalConstraintLayout.setVisibility(View.GONE);
        }

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mVideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }

    private void initView() {
        posterImageView = findViewById(R.id.image_poster);
        descriptionTextView = findViewById(R.id.txtDescription);
        titleTextView = findViewById(R.id.txtTitle);
        releaseDateTextView = findViewById(R.id.txtReleaseDate);
        adultTextView = findViewById(R.id.txtAdult);
        popularityTextView = findViewById(R.id.txtPopularity);
        connectionErrorTextView = findViewById(R.id.connectionError);
        generalConstraintLayout = findViewById(R.id.generalConstraintLayout);
        mYoutubePlayerView = findViewById(R.id.youtubePlayerView);
    }

    private void setPosterImage() {
        posterImageView.setImageBitmap(null);

        Picasso.with(getApplicationContext())
                .load(Constants.IMAGES_URL + mPosterPath)
                .into(posterImageView);
    }

    private void getData(int id) {

        Call<MovieDataResponse> call = apiInterface.getMovieData(id);

        call.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                MovieDataResponse movieDataResponse = response.body();

                titleTextView.setText(movieDataResponse.getTitle());
                releaseDateTextView.setText(movieDataResponse.getRelease_date());
                descriptionTextView.setText(movieDataResponse.getOverview());
                adultTextView.setText(movieDataResponse.getAdult() ? getString(R.string.adult_yes) : getString(R.string.adult_no));
                popularityTextView.setText(movieDataResponse.getPopularity());

                if (movieDataResponse.getVideos().getResults().size() > 0) {
                    if (movieDataResponse.getVideos().getResults().get(0).getSite().equals("YouTube")) {
                        mYoutubePlayerView.setVisibility(View.VISIBLE);
                        mVideoId = movieDataResponse.getVideos().getResults().get(0).getKey();
                        mYoutubePlayerView.initialize(Constants.YOUTUBE_API_KEY,mOnInitializedListener);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getData()");
            }
        });
    }

}
