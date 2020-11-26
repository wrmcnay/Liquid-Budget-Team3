package com.example.liquidbudget.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liquidbudget.data.dao.CategoryDAO;
import com.example.liquidbudget.data.entities.Category;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.liquidbudget.data.database.CategoryDatabase.DATABASE_VERSION;

@Database(entities = Category.class,version = DATABASE_VERSION)
public abstract class CategoryDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Category-Database";

    private static CategoryDatabase cInstance;

    public abstract CategoryDAO categoryDAO();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized CategoryDatabase getInstance(Context context){
        if(cInstance == null){
            synchronized (ExpenseDatabase.class) {
                if (cInstance == null) {
                    cInstance = Room.databaseBuilder(context, CategoryDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return cInstance;
    }

}
