package com.example.liquidbudget.data.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liquidbudget.data.DAO.CategoryDAO;
import com.example.liquidbudget.data.Entities.Category;

import static com.example.liquidbudget.data.Databases.CategoryDatabase.DATABASE_VERSION;

@Database(entities = Category.class,version = DATABASE_VERSION)
public abstract class CategoryDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Category-Database";

    public abstract CategoryDAO categoryDAO();

    private static CategoryDatabase mInstance;

    public static CategoryDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context, CategoryDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

}
