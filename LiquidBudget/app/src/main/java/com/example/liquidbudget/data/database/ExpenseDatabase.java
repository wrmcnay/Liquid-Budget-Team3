package com.example.liquidbudget.data.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liquidbudget.data.dao.ExpenseDAO;
import com.example.liquidbudget.data.database.IncomeDatabase;
import com.example.liquidbudget.data.entities.Expense;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.liquidbudget.data.database.CategoryDatabase.DATABASE_VERSION;

@Database(entities = Expense.class, version = DATABASE_VERSION, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Expense-Database";

    private static ExpenseDatabase eInstance;

    public abstract ExpenseDAO expenseDAO();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ExpenseDatabase getInstance(Context context) {
        if (eInstance == null) {
            synchronized (ExpenseDatabase.class) {
                if (eInstance == null) {
                    eInstance = Room.databaseBuilder(context, ExpenseDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return eInstance;
    }




}
