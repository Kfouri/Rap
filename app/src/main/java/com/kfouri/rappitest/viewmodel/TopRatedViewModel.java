package com.kfouri.rappitest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kfouri.rappitest.persist.dao.TopRatedDao;
import com.kfouri.rappitest.persist.database.Database;
import com.kfouri.rappitest.persist.model.TopRatedModel;

import java.util.List;

public class TopRatedViewModel extends AndroidViewModel {
    private TopRatedDao mTopRatedDao;

    public TopRatedViewModel(@NonNull Application application) {
        super(application);
        Database mDatabase = Database.getDatabase(application);
        mTopRatedDao = mDatabase.topRatedDao();
    }

    public LiveData<List<TopRatedModel>> getTopRatedData() {
        return mTopRatedDao.getTopRated();
    }

    public void insertTopRated(TopRatedModel topRatedModel) {
        new TopRatedViewModel.InsertAsyncTask(mTopRatedDao).execute(topRatedModel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void removeAllData() {
        new TopRatedViewModel.RemoveAllDataAsyncTask(mTopRatedDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<TopRatedModel, Void, Void> {

        TopRatedDao topRatedDao;

        InsertAsyncTask(TopRatedDao topRatedDao) {
            this.topRatedDao = topRatedDao;
        }

        @Override
        protected Void doInBackground(TopRatedModel... topRatedModels) {
            topRatedDao.insert(topRatedModels[0]);
            return null;
        }
    }

    private static class RemoveAllDataAsyncTask extends AsyncTask<Void, Void, Void> {

        TopRatedDao topRatedDao;

        RemoveAllDataAsyncTask(TopRatedDao topRatedDao) {
            this.topRatedDao = topRatedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            topRatedDao.removeAll();
            return null;
        }
    }
}
