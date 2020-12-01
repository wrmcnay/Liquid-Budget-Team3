package com.example.liquidbudget.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liquidbudget.data.dao.UserAccountDAO;
import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.entities.UserAccount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.liquidbudget.data.database.UserAccountDatabase.DATABASE_VERSION;

@Database(entities = {UserAccount.class, Category.class},version = DATABASE_VERSION)
public abstract class UserAccountDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "User-Database";

    public abstract UserAccountDAO userAccountDAO();

    public static UserAccountDatabase mInstance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized UserAccountDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized ((UserAccountDatabase.class)) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context, UserAccountDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return mInstance;
    }
}