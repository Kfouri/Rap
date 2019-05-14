package com.kfouri.rappitest.persist.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kfouri.rappitest.persist.dao.PopularDao;
import com.kfouri.rappitest.persist.dao.TopRatedDao;
import com.kfouri.rappitest.persist.model.PopularModel;
import com.kfouri.rappitest.persist.model.TopRatedModel;

@android.arch.persistence.room.Database(entities = {PopularModel.class, TopRatedModel.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract PopularDao popularDao();
    public abstract TopRatedDao topRatedDao();

    private static volatile Database databaseInstance;

    public static Database getDatabase(final Context context) {

        if (databaseInstance == null) {
            synchronized (Database.class) {
                databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        Database.class, "rappi_database")
                        .build();
            }
        }
        return databaseInstance;
    }
}
