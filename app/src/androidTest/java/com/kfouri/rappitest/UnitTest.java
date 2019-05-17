package com.kfouri.rappitest;

import com.kfouri.rappitest.model.MovieDataResponse;
import com.kfouri.rappitest.model.TvDataResponse;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

public class UnitTest {

    private APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);

    @Test
    public void movieIdTest() {
        Call<MovieDataResponse> call = mApiInterface.getMovieData(299537);
        try {
            Response<MovieDataResponse> response = call.execute();
            MovieDataResponse movieDataResponse = response.body();
            assertEquals(movieDataResponse.getTitle(),"Capitana Marvel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tvIdTest() {
        Call<TvDataResponse> call = mApiInterface.getTvData(1399);
        try {
            Response<TvDataResponse> response = call.execute();
            TvDataResponse tvDataResponse = response.body();
            assertEquals(tvDataResponse.getName(),"Juego de Tronos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
