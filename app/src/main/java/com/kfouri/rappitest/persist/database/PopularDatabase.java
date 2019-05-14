package com.kfouri.rappitest.persist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kfouri.rappitest.persist.dao.PopularDao;
import com.kfouri.rappitest.persist.model.PopularModel;

@Database(entities = PopularModel.class, version = 1, exportSchema = false)
public abstract class PopularDatabase extends RoomDatabase {

    public abstract PopularDao popularDao();

    private static volatile PopularDatabase popularDatabaseInstance;

    public static PopularDatabase getDatabase(final Context context) {

        if (popularDatabaseInstance == null) {
            synchronized (PopularDatabase.class) {
                popularDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PopularDatabase.class, "popular_database")
                        .build();
            }
        }
        return popularDatabaseInstance;
    }
}
