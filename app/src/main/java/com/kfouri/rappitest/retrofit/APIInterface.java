package com.kfouri.rappitest.retrofit;

import com.kfouri.rappitest.model.MovieDataResponse;
import com.kfouri.rappitest.model.MovieResponse;
import com.kfouri.rappitest.model.TvDataResponse;
import com.kfouri.rappitest.model.TvResponse;
import com.kfouri.rappitest.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("4/discover/movie?sort_by=popularity.desc")
    Call<MovieResponse> getPopularMovieList();

    @GET("4/discover/tv?sort_by=popularity.desc")
    Call<TvResponse> getPopularTvList();

    @GET("4/discover/movie?sort_by=vote_average.desc")
    Call<MovieResponse> getTopRatedMovieList();

    @GET("4/discover/tv?sort_by=vote_average.desc")
    Call<TvResponse> getTopRatedTvList();

    @GET("4/discover/movie")
    Call<MovieResponse> getUpcomingMovieList(@Query("primary_release_date.gte") String primary_release_date);

    @GET("4/discover/tv")
    Call<TvResponse> getUpcomingTvList(@Query("first_air_date.gte") String primary_release_date);

    @GET("3/movie/{movieId}?api_key=" + Constants.APIKEY + "&language=es-ES&append_to_response=videos")
    Call<MovieDataResponse> getMovieData(@Path("movieId") Integer movieId);

    @GET("3/tv/{tvId}?api_key=" + Constants.APIKEY + "&language=es-ES&append_to_response=videos")
    Call<TvDataResponse> getTvData(@Path("tvId") Integer tvId);
}
