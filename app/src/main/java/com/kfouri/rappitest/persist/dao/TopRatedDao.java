package com.kfouri.rappitest.persist.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kfouri.rappitest.persist.model.TopRatedModel;

import java.util.List;

@Dao
public interface TopRatedDao {

    @Insert
    void insert(TopRatedModel topRatedModel);

    @Query("DELETE FROM toprated")
    void removeAll();

    @Query("SELECT * FROM toprated ORDER BY toprated_order")
    LiveData<List<TopRatedModel>> getTopRated();
}