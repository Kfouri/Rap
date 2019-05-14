package com.kfouri.rappitest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kfouri.rappitest.persist.dao.PopularDao;
import com.kfouri.rappitest.persist.database.PopularDatabase;
import com.kfouri.rappitest.persist.model.PopularModel;

import java.util.List;

public class PopularViewModel extends AndroidViewModel {
    private PopularDao mPopularDao;

    public PopularViewModel(@NonNull Application application) {
        super(application);
        PopularDatabase mPopularDatabase = PopularDatabase.getDatabase(application);
        mPopularDao = mPopularDatabase.popularDao();
    }

    public LiveData<List<PopularModel>> getPopularData() {
        return mPopularDao.getPopular();
    }

    public void insertPopular(PopularModel popularModel) {
        new InsertAsyncTask(mPopularDao).execute(popularModel);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void removeAllData() {
        new RemoveAllDataAsyncTask(mPopularDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<PopularModel, Void, Void> {

        PopularDao popularDao;

        InsertAsyncTask(PopularDao popularDao) {
            this.popularDao = popularDao;
        }

        @Override
        protected Void doInBackground(PopularModel... popularModels) {
            popularDao.insert(popularModels[0]);
            return null;
        }
    }

    private static class RemoveAllDataAsyncTask extends AsyncTask<Void, Void, Void> {

        PopularDao popularDao;

        RemoveAllDataAsyncTask(PopularDao popularDao) {
            this.popularDao = popularDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            popularDao.removeAll();
            return null;
        }
    }
}
