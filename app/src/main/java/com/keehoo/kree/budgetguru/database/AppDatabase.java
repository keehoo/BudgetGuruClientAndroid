package com.keehoo.kree.budgetguru.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.keehoo.kree.budgetguru.daos.BudgetEntryDao;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.database.converters.BudgetItemConverter;

/**
 * Created by krzysztof on 03.12.2017.
 */


@Database(entities = {BudgetEntryModel.class}, version = 1)
@TypeConverters({BudgetItemConverter.class})

public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;

    public abstract BudgetEntryDao budgetEntryDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "budgetentry-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
