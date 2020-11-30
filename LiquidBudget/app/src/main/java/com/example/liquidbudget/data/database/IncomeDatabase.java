package com.example.liquidbudget.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.liquidbudget.data.dao.IncomeDAO;
import com.example.liquidbudget.data.entities.Income;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.liquidbudget.data.database.CategoryDatabase.DATABASE_VERSION;

@Database(entities = Income.class, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class IncomeDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Income-Database";

    public abstract IncomeDAO incomeDAO();

    private static IncomeDatabase mInstance;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized IncomeDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (IncomeDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context, IncomeDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return mInstance;
    }

}
