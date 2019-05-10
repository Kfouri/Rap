package com.kfouri.rappitest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.kfouri.rappitest.R;
import com.kfouri.rappitest.adapter.GenericAdapter;
import com.kfouri.rappitest.model.MovieResponse;
import com.kfouri.rappitest.model.TvResponse;
import com.kfouri.rappitest.model.Video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private APIInterface apiInterface;
    private ArrayList mVideoPopularList;
    private ArrayList mVideoTopRatedList;
    private ArrayList mVideoUpcomingList;
    private boolean mIsTvPopularResponded;
    private boolean mIsMoviePopularResponded;
    private boolean mIsTvTopRatedResponded;
    private boolean mIsMovieTopRatedResponded;
    private boolean mIsTvUpcomingResponded;
    private boolean mIsMovieUpcomingResponded;

    private GenericAdapter mPopularAdapter;
    private GenericAdapter mTopRatedAdapter;
    private GenericAdapter mUpcomingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoPopularList = new ArrayList();
        mVideoTopRatedList = new ArrayList();
        mVideoUpcomingList = new ArrayList();

        apiInterface = APIClient.getClient().create(APIInterface.class);

        RecyclerView mPopularRecycler = findViewById(R.id.popularList);
        RecyclerView mTopRatedRecycler = findViewById(R.id.topRatedList);
        RecyclerView mUpcomingRecycler = findViewById(R.id.upcomingList);

        RecyclerView.LayoutManager mPopularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mTopRatedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mUpcomingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mPopularRecycler.setLayoutManager(mPopularLayoutManager);
        mTopRatedRecycler.setLayoutManager(mTopRatedLayoutManager);
        mUpcomingRecycler.setLayoutManager(mUpcomingLayoutManager);

        mPopularAdapter = new GenericAdapter(this);
        mTopRatedAdapter = new GenericAdapter(this);
        mUpcomingAdapter = new GenericAdapter(this);

        mPopularRecycler.setAdapter(mPopularAdapter);
        mTopRatedRecycler.setAdapter(mTopRatedAdapter);
        mUpcomingRecycler.setAdapter(mUpcomingAdapter);

        getTvPopularList();
        getMoviePopularList();

        getTvTopRatedeList();
        getMovieTopRatedList();

        getMovieUpcomingList();
        getTvUpcomingList();
    }

    private void getTvPopularList() {
        Call<TvResponse> call = apiInterface.getPopularTvList();

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse movieResponse = response.body();

                mVideoPopularList.addAll(movieResponse.getResults());
                mIsTvPopularResponded = true;
                reorderPopularList();
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getTvPopularList()");
            }
        });
    }

    private void getMoviePopularList() {
        Call<MovieResponse> call = apiInterface.getPopularMovieList();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();

                mVideoPopularList.addAll(movieResponse.getResults());
                mIsMoviePopularResponded = true;
                reorderPopularList();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getMoviePopularList()");
            }
        });
    }

    private void getTvTopRatedeList() {
        Call<TvResponse> call = apiInterface.getTopRatedTvList();

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse movieResponse = response.body();
                mIsTvTopRatedResponded = true;

                if (movieResponse != null) {
                    mVideoTopRatedList.addAll(movieResponse.getResults());
                    reorderTopRatedList();
                }

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getTvTopRatedeList()");
            }
        });
    }

    private void getMovieTopRatedList() {
        Call<MovieResponse> call = apiInterface.getTopRatedMovieList();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();
                mIsMovieTopRatedResponded = true;

                if (movieResponse != null) {
                    mVideoTopRatedList.addAll(movieResponse.getResults());
                    reorderTopRatedList();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getMovieTopRatedList()");
            }
        });
    }

    private void getMovieUpcomingList() {
        Call<MovieResponse> call = apiInterface.getUpcomingMovieList("2019-05-09");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();

                if (movieResponse != null) {
                    mVideoUpcomingList.addAll(movieResponse.getResults());
                    mIsMovieUpcomingResponded = true;
                    collectUpcomingData();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getMovieUpcomingList()");
            }
        });
    }

    private void getTvUpcomingList() {
        Call<TvResponse> call = apiInterface.getUpcomingTvList("2019-05-09");

        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                TvResponse tvResponse = response.body();

                if (tvResponse != null) {
                    mVideoUpcomingList.addAll(tvResponse.getResults());
                    mIsTvUpcomingResponded = true;
                    collectUpcomingData();
                }

            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure getTvUpcomingList()");
            }
        });
    }

    private void collectUpcomingData() {
        if (mIsMovieUpcomingResponded && mIsTvUpcomingResponded) {
            mUpcomingAdapter.setData(mVideoUpcomingList);
        }
    }

    private void reorderPopularList() {
        if (mIsMoviePopularResponded && mIsTvPopularResponded) {
            Collections.sort(mVideoPopularList, new Comparator<Video>(){
                public int compare(Video obj1, Video obj2) {
                    return obj2.getPopularity().compareTo(obj1.getPopularity());
                }
            });

            mPopularAdapter.setData(mVideoPopularList);
        }
    }

    private void reorderTopRatedList() {
        if (mIsMovieTopRatedResponded && mIsTvTopRatedResponded) {
            Collections.sort(mVideoTopRatedList, new Comparator<Video>(){
                public int compare(Video obj1, Video obj2) {
                    return obj2.getVote_average().compareTo(obj1.getVote_average());
                }
            });

            mTopRatedAdapter.setData(mVideoTopRatedList);
        }
    }

}
