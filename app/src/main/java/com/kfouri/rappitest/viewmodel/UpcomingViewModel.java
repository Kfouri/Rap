package com.kfouri.rappitest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kfouri.rappitest.persist.dao.UpcomingDao;
import com.kfouri.rappitest.persist.database.Database;
import com.kfouri.rappitest.persist.model.UpcomingModel;

import java.util.List;

public class UpcomingViewModel extends AndroidViewModel {
    private UpcomingDao mUpcomingDao;

    public UpcomingViewModel(@NonNull Application application) {
        super(application);
        Database mDatabase = Database.getDatabase(application);
        mUpcomingDao = mDatabase.upcomingDao();
    }

    public LiveData<List<UpcomingModel>> getUpcomingData() {
        return mUpcomingDao.getUpcoming();
    }

    public void insertUpcoming(UpcomingModel upcomingModel) {
        new UpcomingViewModel.InsertAsyncTask(mUpcomingDao).execute(upcomingModel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void removeAllData() {
        new UpcomingViewModel.RemoveAllDataAsyncTask(mUpcomingDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<UpcomingModel, Void, Void> {

        UpcomingDao upcomingDao;

        InsertAsyncTask(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        @Override
        protected Void doInBackground(UpcomingModel... upcomingModels) {
            upcomingDao.insert(upcomingModels[0]);
            return null;
        }
    }

    private static class RemoveAllDataAsyncTask extends AsyncTask<Void, Void, Void> {

        UpcomingDao upcomingDao;

        RemoveAllDataAsyncTask(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            upcomingDao.removeAll();
            return null;
        }
    }
}
