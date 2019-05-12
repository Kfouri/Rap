package com.kfouri.rappitest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.model.MovieDataResponse;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.kfouri.rappitest.util.Constants;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataActivity extends AppCompatActivity {

    private static final String TAG = "MovieDataActivity";

    private ImageView posterImageView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private TextView releaseDateTextView;
    private TextView adultTextView;
    private TextView popularityTextView;

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
    }

    private void initView() {
        posterImageView = (ImageView) findViewById(R.id.image_poster);
        descriptionTextView = (TextView) findViewById(R.id.txtDescription);
        titleTextView = (TextView) findViewById(R.id.txtTitle);
        releaseDateTextView = (TextView) findViewById(R.id.txtReleaseDate);
        adultTextView = (TextView) findViewById(R.id.txtAdult);
        popularityTextView = (TextView) findViewById(R.id.txtPopularity);
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

                if (movieDataResponse.getVideo()) {
                    Toast.makeText(getApplicationContext(), "Tiene Video " + movieDataResponse.getId(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getData()");
            }
        });
    }

}
