package com.kfouri.rappitest.retrofit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.kfouri.rappitest.model.MovieResponse;
import com.kfouri.rappitest.model.TvResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoRepository {
    private APIInterface mApiInterface;

    private static VideoRepository videoRepository;

    private VideoRepository() {
        mApiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public synchronized static VideoRepository getInstance() {

        if (videoRepository == null) {
            if (videoRepository == null) {
                videoRepository = new VideoRepository();
            }
        }
        return videoRepository;
    }

    public LiveData<MovieResponse> getMoviePopular() {

        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        mApiInterface.getPopularMovieList().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                    data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<TvResponse> getTvPopular() {

        final MutableLiveData<TvResponse> data = new MutableLiveData<>();

        mApiInterface.getPopularTvList().enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<MovieResponse> getMovieTopRated() {

        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        mApiInterface.getTopRatedMovieList().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<TvResponse> getTvTopRated() {

        final MutableLiveData<TvResponse> data = new MutableLiveData<>();

        mApiInterface.getTopRatedTvList().enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<MovieResponse> getMovieUpcoming(String date) {

        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();

        mApiInterface.getUpcomingMovieList(date).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<TvResponse> getTvUpcoming(String date) {

        final MutableLiveData<TvResponse> data = new MutableLiveData<>();

        mApiInterface.getUpcomingTvList(date).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
