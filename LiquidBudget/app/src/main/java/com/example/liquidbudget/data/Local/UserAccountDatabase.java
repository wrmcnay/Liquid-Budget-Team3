package com.example.liquidbudget.data.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liquidbudget.data.model.UserAccount;

import static com.example.liquidbudget.data.Local.UserAccountDatabase.DATABASE_VERSION;

@Database(entities = UserAccount.class,version = DATABASE_VERSION)
public abstract class UserAccountDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User-Database";

    public abstract UserAccountDAO settingsDAO();

    public static UserAccountDatabase mInstance;

    public static UserAccountDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, UserAccountDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}