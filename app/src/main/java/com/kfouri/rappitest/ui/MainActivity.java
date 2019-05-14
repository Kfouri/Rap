package com.kfouri.rappitest.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.kfouri.rappitest.R;
import com.kfouri.rappitest.adapter.GenericAdapter;
import com.kfouri.rappitest.model.Movie;
import com.kfouri.rappitest.model.MovieResponse;
import com.kfouri.rappitest.model.Tv;
import com.kfouri.rappitest.model.TvResponse;
import com.kfouri.rappitest.model.Video;
import com.kfouri.rappitest.persist.model.PopularModel;
import com.kfouri.rappitest.retrofit.APIClient;
import com.kfouri.rappitest.retrofit.APIInterface;
import com.kfouri.rappitest.util.Constants;
import com.kfouri.rappitest.util.Utils;
import com.kfouri.rappitest.viewmodel.PopularViewModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private APIInterface apiInterface;
    private ArrayList<Video> mVideoPopularList;
    private ArrayList<Video> mVideoTopRatedList;
    private ArrayList<Video> mVideoUpcomingList;
    private boolean mIsTvPopularResponded;
    private boolean mIsMoviePopularResponded;
    private boolean mIsTvTopRatedResponded;
    private boolean mIsMovieTopRatedResponded;
    private boolean mIsTvUpcomingResponded;
    private boolean mIsMovieUpcomingResponded;
    private EditText popularFilter;
    private EditText topRatedFilter;
    private EditText upcomingFilter;
    private GenericAdapter mPopularAdapter;
    private GenericAdapter mTopRatedAdapter;
    private GenericAdapter mUpcomingAdapter;
    private PopularViewModel popularViewModel;
    private boolean mWriteExternalStorageGranted;

    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoPopularList = new ArrayList();
        mVideoTopRatedList = new ArrayList();
        mVideoUpcomingList = new ArrayList();

        apiInterface = APIClient.getClient().create(APIInterface.class);

        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel.class);

        RecyclerView mPopularRecycler = findViewById(R.id.popularList);
        RecyclerView mTopRatedRecycler = findViewById(R.id.topRatedList);
        RecyclerView mUpcomingRecycler = findViewById(R.id.upcomingList);
        popularFilter = findViewById(R.id.popularFilter);
        topRatedFilter = findViewById(R.id.topRatedFilter);
        upcomingFilter = findViewById(R.id.upcomingFilter);

        RecyclerView.LayoutManager mPopularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mTopRatedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mUpcomingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mPopularRecycler.setLayoutManager(mPopularLayoutManager);
        mTopRatedRecycler.setLayoutManager(mTopRatedLayoutManager);
        mUpcomingRecycler.setLayoutManager(mUpcomingLayoutManager);

        mPopularAdapter = new GenericAdapter(this);
        mTopRatedAdapter = new GenericAdapter(this);
        mUpcomingAdapter = new GenericAdapter(this);

        setFilter();

        mPopularRecycler.setAdapter(mPopularAdapter);
        mTopRatedRecycler.setAdapter(mTopRatedAdapter);
        mUpcomingRecycler.setAdapter(mUpcomingAdapter);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    private void setFilter() {

        popularFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPopularAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        topRatedFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTopRatedAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        upcomingFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUpcomingAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
        //TODO getToday
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
        //TODO get today
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
            Log.d(TAG, "collectUpcomingData");
            // Eliminar y Guardar Datos en BBDD
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
            Log.d(TAG, "reorderPopularList");

            if (mWriteExternalStorageGranted && Utils.isNetworkAvailable(this)) {

                popularViewModel.removeAllData();

                int order = 0;
                for (Video video : mVideoPopularList) {
                    order++;
                    PopularModel popularModel = new PopularModel();

                    popularModel.setId(video.getId());
                    popularModel.setName((video instanceof Movie) ? ((Movie)video).getTitle() : ((Tv) video).getName());
                    popularModel.setPoster_path(video.getPoster_path());
                    popularModel.setPopular_order(order);
                    popularModel.setMovie((video instanceof Movie));
                    popularViewModel.insertPopular(popularModel);

                    File file = new File(Constants.PATH + "/" + Constants.FOLDER_NAME + video.getPoster_path());
                    if (!file.exists()) {
                        Log.d(TAG, "Downloading " + video.getPoster_path());
                        new DownloadImageAsnyc(video.getPoster_path()).execute(Constants.IMAGES_URL);
                    } else {
                        Log.d(TAG, "File " + video.getPoster_path() + " exist");
                    }
                }

            }

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
            //Log.d(TAG, "reorderTopRatedList path "+this.getFilesDir().getPath());
            // Eliminar y Guardar Datos en BBDD

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        mWriteExternalStorageGranted = true;
                        if (Utils.isNetworkAvailable(this)) {
                            deleteRecursive(new File(Constants.PATH, Constants.FOLDER_NAME));
                            createFolder();
                        }
                    } else {
                        mWriteExternalStorageGranted = false;
                        Toast.makeText(this, "Offline disabled", Toast.LENGTH_LONG).show();
                    }

                    if (Utils.isNetworkAvailable(this)) {

                        getTvPopularList();
                        getMoviePopularList();

                        getTvTopRatedeList();
                        getMovieTopRatedList();

                        getMovieUpcomingList();
                        getTvUpcomingList();

                    } else {
                        if (mWriteExternalStorageGranted) {
                            Toast.makeText(this, "Offline mode", Toast.LENGTH_LONG).show();
                            //TODO leer de BBDD y transformar a cada lista
                            popularViewModel.getPopularData().observe(this, new Observer<List<PopularModel>>() {
                                @Override
                                public void onChanged(@Nullable List<PopularModel> popularModels) {
                                    Video video;
                                    for (PopularModel popularModel : popularModels) {
                                        Log.d(TAG, popularModel.getName());

                                        if (popularModel.getMovie()) {
                                            Movie movie = new Movie();

                                            movie.setId(popularModel.getId());
                                            movie.setTitle(popularModel.getName());
                                            movie.setPoster_path(popularModel.getPoster_path());
                                            mVideoPopularList.add(movie);
                                        } else {
                                            Tv tv = new Tv();

                                            tv.setId(popularModel.getId());
                                            tv.setName(popularModel.getName());
                                            tv.setPoster_path(popularModel.getPoster_path());
                                            mVideoPopularList.add(tv);
                                        }
                                    }
                                    mPopularAdapter.setData(mVideoPopularList);
                                }
                            });
                        } else {
                            Toast.makeText(this, "Offline mode without permission", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        }
    }

    void deleteRecursive(File fileOrDirectory) {
        Log.d(TAG, "Deleting Folder");
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    private void createFolder() {
        Log.d(TAG, "Creating Folder");
        File f = new File(Constants.PATH, Constants.FOLDER_NAME);
        if (!f.exists()) {
            if (f.mkdirs()) {
                Log.d(TAG, "Folder " + Constants.PATH + "/" + Constants.FOLDER_NAME + " created");
            }
        }
    }

    public static Boolean saveBitmapToJPEGFile(Bitmap theTempBitmap, File theTargetFile) {
        boolean result = true;
        if (theTempBitmap != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(theTargetFile);
                theTempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (FileNotFoundException e) {
                result = false;
                e.printStackTrace();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    public static class DownloadImageAsnyc extends AsyncTask<String, Void, Bitmap> {

        String downloadPath = "";
        String mFileName;

        DownloadImageAsnyc(String fileName) {
            mFileName = fileName;
        }

        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                downloadPath = args[0];
                return BitmapFactory.decodeStream((InputStream) new URL(downloadPath + mFileName).getContent());

            } catch (IOException e) {
                Log.d(TAG, "Error downloading " + downloadPath + mFileName);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                File file = new File(Constants.PATH + "/" + Constants.FOLDER_NAME + "/" + mFileName);

                if (saveBitmapToJPEGFile(bitmap, file)) {
                    Log.d(TAG, "File " + file.getName() + " Saved");
                } else {
                    Log.d(TAG, "File " + file.getName() + " NOT Saved");
                }
//
//                if (mFileName.equals("mo0FP1GxOFZT4UDde7RFDz5APXF.jpg")) {
//                    mImage.setImageDrawable(getBitmap(PATH + "/" + FOLDER_NAME + "/" + "mo0FP1GxOFZT4UDde7RFDz5APXF.jpg"));
//                }
            }
        }
    }
}

