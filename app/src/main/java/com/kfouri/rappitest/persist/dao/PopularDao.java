package com.kfouri.rappitest.persist.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kfouri.rappitest.persist.model.PopularModel;

import java.util.List;

@Dao
public interface PopularDao {

    @Insert
    void insert(PopularModel popularModel);

    @Query("DELETE FROM popular")
    void removeAll();

    @Query("SELECT * FROM popular ORDER BY popular_order")
    LiveData<List<PopularModel>> getPopular();
}
