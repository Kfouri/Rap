package com.kfouri.rappitest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kfouri.rappitest.persist.dao.PopularDao;
import com.kfouri.rappitest.persist.dao.TopRatedDao;
import com.kfouri.rappitest.persist.dao.UpcomingDao;
import com.kfouri.rappitest.persist.database.Database;
import com.kfouri.rappitest.persist.model.PopularModel;
import com.kfouri.rappitest.persist.model.TopRatedModel;
import com.kfouri.rappitest.persist.model.UpcomingModel;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private PopularDao mPopularDao;
    private TopRatedDao mTopRatedDao;
    private UpcomingDao mUpcomingDao;

    public LiveData<List<PopularModel>> getPopularData() {
        return mPopularDao.getPopular();
    }
    public LiveData<List<TopRatedModel>> getTopRatedData() {
        return mTopRatedDao.getTopRated();
    }
    public LiveData<List<UpcomingModel>> getUpcomingData() {
        return mUpcomingDao.getUpcoming();
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        Database mDatabase = Database.getDatabase(application);
        mPopularDao = mDatabase.popularDao();
        mTopRatedDao = mDatabase.topRatedDao();
        mUpcomingDao = mDatabase.upcomingDao();
    }

    public void insertPopular(PopularModel popularModel) {
        new MainActivityViewModel.InsertPopularAsyncTask(mPopularDao).execute(popularModel);
    }

    public void insertUpcoming(UpcomingModel upcomingModel) {
        new MainActivityViewModel.InsertUpcomingAsyncTask(mUpcomingDao).execute(upcomingModel);
    }

    public void insertTopRated(TopRatedModel topRatedModel) {
        new MainActivityViewModel.InsertTopRatedAsyncTask(mTopRatedDao).execute(topRatedModel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void removeAllPopularData() {
        new MainActivityViewModel.RemoveAllPopularDataAsyncTask(mPopularDao).execute();
    }

    public void removeAllUpcomingData() {
        new MainActivityViewModel.RemoveAllUpcomingDataAsyncTask(mUpcomingDao).execute();
    }

    public void removeAllTopRatedData() {
        new MainActivityViewModel.RemoveAllTopRatedDataAsyncTask(mTopRatedDao).execute();
    }

    private static class InsertPopularAsyncTask extends AsyncTask<PopularModel, Void, Void> {

        PopularDao popularDao;

        InsertPopularAsyncTask(PopularDao popularDao) {
            this.popularDao = popularDao;
        }

        @Override
        protected Void doInBackground(PopularModel... popularModels) {
            popularDao.insert(popularModels[0]);
            return null;
        }
    }

    private static class RemoveAllPopularDataAsyncTask extends AsyncTask<Void, Void, Void> {

        PopularDao popularDao;

        RemoveAllPopularDataAsyncTask(PopularDao popularDao) {
            this.popularDao = popularDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            popularDao.removeAll();
            return null;
        }
    }

    private static class InsertUpcomingAsyncTask extends AsyncTask<UpcomingModel, Void, Void> {

        UpcomingDao upcomingDao;

        InsertUpcomingAsyncTask(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        @Override
        protected Void doInBackground(UpcomingModel... upcomingModels) {
            upcomingDao.insert(upcomingModels[0]);
            return null;
        }
    }

    private static class RemoveAllUpcomingDataAsyncTask extends AsyncTask<Void, Void, Void> {

        UpcomingDao upcomingDao;

        RemoveAllUpcomingDataAsyncTask(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            upcomingDao.removeAll();
            return null;
        }
    }

    private static class InsertTopRatedAsyncTask extends AsyncTask<TopRatedModel, Void, Void> {

        TopRatedDao topRatedDao;

        InsertTopRatedAsyncTask(TopRatedDao topRatedDao) {
            this.topRatedDao = topRatedDao;
        }

        @Override
        protected Void doInBackground(TopRatedModel... topRatedModels) {
            topRatedDao.insert(topRatedModels[0]);
            return null;
        }
    }

    private static class RemoveAllTopRatedDataAsyncTask extends AsyncTask<Void, Void, Void> {

        TopRatedDao topRatedDao;

        RemoveAllTopRatedDataAsyncTask(TopRatedDao topRatedDao) {
            this.topRatedDao = topRatedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            topRatedDao.removeAll();
            return null;
        }
    }
}
