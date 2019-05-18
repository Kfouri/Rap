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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.kfouri.rappitest.R;
import com.kfouri.rappitest.adapter.GenericAdapter;
import com.kfouri.rappitest.model.Movie;
import com.kfouri.rappitest.model.MovieResponse;
import com.kfouri.rappitest.model.Tv;
import com.kfouri.rappitest.model.TvResponse;
import com.kfouri.rappitest.model.Video;
import com.kfouri.rappitest.persist.model.PopularModel;
import com.kfouri.rappitest.persist.model.TopRatedModel;
import com.kfouri.rappitest.persist.model.UpcomingModel;
import com.kfouri.rappitest.util.Constants;
import com.kfouri.rappitest.util.Utils;
import com.kfouri.rappitest.viewmodel.MainActivityViewModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Video> mVideoPopularList;
    private ArrayList<Video> mVideoTopRatedList;
    private ArrayList<Video> mVideoUpcomingList;
    private boolean mIsTvPopularResponded;
    private boolean mIsMoviePopularResponded;
    private boolean mIsTvTopRatedResponded;
    private boolean mIsMovieTopRatedResponded;
    private boolean mIsTvUpcomingResponded;
    private boolean mIsMovieUpcomingResponded;
    private EditText mPopularFilter;
    private EditText mTopRatedFilter;
    private EditText mUpcomingFilter;
    private TextView mOfflineMode;
    private GenericAdapter mPopularAdapter;
    private GenericAdapter mTopRatedAdapter;
    private GenericAdapter mUpcomingAdapter;
    private MainActivityViewModel mMainActivityViewModel;
    private TextView mPopularTitle;
    private TextView mTopRatedTitle;
    private TextView mUpcomingTitle;

    private boolean mWriteExternalStorageGranted;

    private RecyclerView mPopularRecycler;
    private RecyclerView mTopRatedRecycler;
    private RecyclerView mUpcomingRecycler;

    private static final int REQUEST_CODE = 1;

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoPopularList = new ArrayList();
        mVideoTopRatedList = new ArrayList();
        mVideoUpcomingList = new ArrayList();

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        initView();
        createLayoutManager();

        mPopularAdapter = new GenericAdapter(this);
        mTopRatedAdapter = new GenericAdapter(this);
        mUpcomingAdapter = new GenericAdapter(this);

        setFilter();

        mPopularRecycler.setAdapter(mPopularAdapter);
        mTopRatedRecycler.setAdapter(mTopRatedAdapter);
        mUpcomingRecycler.setAdapter(mUpcomingAdapter);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        mShimmerViewContainer.startShimmerAnimation();
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
                            Utils.deleteRecursive(new File(Constants.PATH, Constants.FOLDER_NAME));
                            Utils.createFolder();
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
                        showUI();
                        if (mWriteExternalStorageGranted) {
                            mOfflineMode.setVisibility(View.VISIBLE);
                            mMainActivityViewModel.getPopularData().observe(this, new Observer<List<PopularModel>>() {
                                @Override
                                public void onChanged(@Nullable List<PopularModel> popularModels) {
                                    if (popularModels != null) {
                                        for (PopularModel popularModel : popularModels) {
                                            Log.d(TAG, "Popular name: " + popularModel.getName());

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
                                    }
                                    mPopularAdapter.setData(mVideoPopularList);
                                }
                            });

                            mMainActivityViewModel.getTopRatedData().observe(this, new Observer<List<TopRatedModel>>() {
                                @Override
                                public void onChanged(@Nullable List<TopRatedModel> topRatedModels) {
                                    if (topRatedModels != null) {
                                        for (TopRatedModel topRatedModel : topRatedModels) {
                                            Log.d(TAG, "TopRated name: " + topRatedModel.getName());

                                            if (topRatedModel.getMovie()) {
                                                Movie movie = new Movie();

                                                movie.setId(topRatedModel.getId());
                                                movie.setTitle(topRatedModel.getName());
                                                movie.setPoster_path(topRatedModel.getPoster_path());
                                                mVideoTopRatedList.add(movie);
                                            } else {
                                                Tv tv = new Tv();

                                                tv.setId(topRatedModel.getId());
                                                tv.setName(topRatedModel.getName());
                                                tv.setPoster_path(topRatedModel.getPoster_path());
                                                mVideoTopRatedList.add(tv);
                                            }
                                        }
                                    }
                                    mTopRatedAdapter.setData(mVideoTopRatedList);
                                }
                            });

                            mMainActivityViewModel.getUpcomingData().observe(this, new Observer<List<UpcomingModel>>() {
                                @Override
                                public void onChanged(@Nullable List<UpcomingModel> upcomingModels) {
                                    if (upcomingModels != null) {
                                        for (UpcomingModel upcomingModel : upcomingModels) {
                                            Log.d(TAG, "Upcoming name: " + upcomingModel.getName() + " " + upcomingModel.getPoster_path());

                                            if (upcomingModel.getMovie()) {
                                                Movie movie = new Movie();

                                                movie.setId(upcomingModel.getId());
                                                movie.setTitle(upcomingModel.getName());
                                                movie.setPoster_path(upcomingModel.getPoster_path());
                                                mVideoUpcomingList.add(movie);
                                            } else {
                                                Tv tv = new Tv();

                                                tv.setId(upcomingModel.getId());
                                                tv.setName(upcomingModel.getName());
                                                tv.setPoster_path(upcomingModel.getPoster_path());
                                                mVideoUpcomingList.add(tv);
                                            }
                                        }
                                    }
                                    mUpcomingAdapter.setData(mVideoUpcomingList);
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

    private void initView() {
        mPopularRecycler = findViewById(R.id.popularList);
        mTopRatedRecycler = findViewById(R.id.topRatedList);
        mUpcomingRecycler = findViewById(R.id.upcomingList);
        mPopularFilter = findViewById(R.id.popularFilter);
        mTopRatedFilter = findViewById(R.id.topRatedFilter);
        mUpcomingFilter = findViewById(R.id.upcomingFilter);
        mOfflineMode = findViewById(R.id.offlineMode);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mPopularTitle = findViewById(R.id.popularTitle);
        mTopRatedTitle = findViewById(R.id.topRatedTitle);
        mUpcomingTitle = findViewById(R.id.upcomingTitle);
    }

    private void createLayoutManager() {
        RecyclerView.LayoutManager mPopularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mTopRatedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mUpcomingLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mPopularRecycler.setLayoutManager(mPopularLayoutManager);
        mTopRatedRecycler.setLayoutManager(mTopRatedLayoutManager);
        mUpcomingRecycler.setLayoutManager(mUpcomingLayoutManager);
    }

    private void setFilter() {

        mPopularFilter.addTextChangedListener(new TextWatcher() {
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

        mTopRatedFilter.addTextChangedListener(new TextWatcher() {
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

        mUpcomingFilter.addTextChangedListener(new TextWatcher() {
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
        mMainActivityViewModel.getTvPopular().observe(this, new Observer<TvResponse>() {
            @Override
            public void onChanged(@Nullable TvResponse data) {
                if (data != null) {
                    mVideoPopularList.addAll(data.getResults());
                    mIsTvPopularResponded = true;
                    reorderPopularList();
                }
            }
        });
    }

    private void getMoviePopularList() {
        mMainActivityViewModel.getMoviePopular().observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(@Nullable MovieResponse data) {
                if (data != null) {
                    mVideoPopularList.addAll(data.getResults());
                    mIsMoviePopularResponded = true;
                    reorderPopularList();
                }
            }
        });
    }

    private void getTvTopRatedeList() {
        mMainActivityViewModel.getTvTopRated().observe(this, new Observer<TvResponse>() {
            @Override
            public void onChanged(@Nullable TvResponse data) {
                if (data != null) {
                    mVideoTopRatedList.addAll(data.getResults());
                    mIsTvTopRatedResponded = true;
                    reorderTopRatedList();
                }
            }
        });
    }

    private void getMovieTopRatedList() {
        mMainActivityViewModel.getMovieTopRated().observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(@Nullable MovieResponse data) {
                if (data != null) {
                    mVideoTopRatedList.addAll(data.getResults());
                    mIsMovieTopRatedResponded = true;
                    reorderTopRatedList();
                }
            }
        });
    }

    private void getMovieUpcomingList() {
        mMainActivityViewModel.getMovieUpcoming().observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(@Nullable MovieResponse data) {
                if (data != null) {
                    mVideoUpcomingList.addAll(data.getResults());
                    mIsMovieUpcomingResponded = true;
                    collectUpcomingData();
                }
            }
        });
    }

    private void getTvUpcomingList() {
        mMainActivityViewModel.getTvUpcoming().observe(this, new Observer<TvResponse>() {
            @Override
            public void onChanged(@Nullable TvResponse data) {
                if (data != null) {
                    mVideoUpcomingList.addAll(data.getResults());
                    mIsTvUpcomingResponded = true;
                    collectUpcomingData();
                }
            }
        });
    }

    private void collectUpcomingData() {
        if (mIsMovieUpcomingResponded && mIsTvUpcomingResponded) {
            mUpcomingAdapter.setData(mVideoUpcomingList);

            showUI();

            if (mWriteExternalStorageGranted && Utils.isNetworkAvailable(this)) {
                mMainActivityViewModel.removeAllUpcomingData();

                int order = 0;
                for (Video video : mVideoUpcomingList) {
                    if (video.getPoster_path() != null) {
                        order++;
                        UpcomingModel upcomingModel = new UpcomingModel();
                        upcomingModel.setId(video.getId());
                        upcomingModel.setName((video instanceof Movie) ? ((Movie)video).getTitle() : ((Tv) video).getName());
                        upcomingModel.setPoster_path(video.getPoster_path());
                        upcomingModel.setUpcoming_order(order);
                        upcomingModel.setMovie((video instanceof Movie));
                        mMainActivityViewModel.insertUpcoming(upcomingModel);

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

                mMainActivityViewModel.removeAllPopularData();

                int order = 0;
                for (Video video : mVideoPopularList) {
                    if (video.getPoster_path() != null) {
                        order++;
                        PopularModel popularModel = new PopularModel();

                        popularModel.setId(video.getId());
                        popularModel.setName((video instanceof Movie) ? ((Movie) video).getTitle() : ((Tv) video).getName());
                        popularModel.setPoster_path(video.getPoster_path());
                        popularModel.setPopular_order(order);
                        popularModel.setMovie((video instanceof Movie));
                        mMainActivityViewModel.insertPopular(popularModel);

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
    }

    private void reorderTopRatedList() {
        if (mIsMovieTopRatedResponded && mIsTvTopRatedResponded) {
            Collections.sort(mVideoTopRatedList, new Comparator<Video>(){
                public int compare(Video obj1, Video obj2) {
                    return obj2.getVote_average().compareTo(obj1.getVote_average());
                }
            });

            mTopRatedAdapter.setData(mVideoTopRatedList);

            if (mWriteExternalStorageGranted && Utils.isNetworkAvailable(this)) {
                mMainActivityViewModel.removeAllTopRatedData();

                int order = 0;
                for (Video video : mVideoTopRatedList) {
                    if (video.getPoster_path() != null) {
                        order++;
                        TopRatedModel topRatedModel = new TopRatedModel();

                        topRatedModel.setId(video.getId());
                        topRatedModel.setName((video instanceof Movie) ? ((Movie)video).getTitle() : ((Tv) video).getName());
                        topRatedModel.setPoster_path(video.getPoster_path());
                        topRatedModel.setToprated_order(order);
                        topRatedModel.setMovie((video instanceof Movie));
                        mMainActivityViewModel.insertTopRated(topRatedModel);

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

                if (Utils.saveBitmapToJPEGFile(bitmap, file)) {
                    Log.d(TAG, "File " + file.getName() + " Saved");
                } else {
                    Log.d(TAG, "File " + file.getName() + " NOT Saved");
                }
            }
        }
    }

    private void showUI() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);

        mPopularTitle.setVisibility(View.VISIBLE);
        mTopRatedTitle.setVisibility(View.VISIBLE);
        mUpcomingTitle.setVisibility(View.VISIBLE);

        mPopularFilter.setVisibility(View.VISIBLE);
        mTopRatedFilter.setVisibility(View.VISIBLE);
        mUpcomingFilter.setVisibility(View.VISIBLE);
    }
}

