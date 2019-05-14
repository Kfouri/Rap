package com.kfouri.rappitest.persist.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kfouri.rappitest.persist.model.UpcomingModel;

import java.util.List;

@Dao
public interface UpcomingDao {

    @Insert
    void insert(UpcomingModel upcomingModel);

    @Query("DELETE FROM upcoming")
    void removeAll();

    @Query("SELECT * FROM upcoming ORDER BY upcoming_order")
    LiveData<List<UpcomingModel>> getUpcoming();
}