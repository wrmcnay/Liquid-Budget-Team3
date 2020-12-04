package com.example.liquidbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.entities.Expense;

import java.util.List;

@Dao
public interface ExpenseDAO {

    @Insert
    void insertExpense(Expense... expenses);

    @Update
    void updateExpense(Expense... expenses);

    @Delete
    void deleteExpense(Expense expenses);

    @Query("SELECT * FROM expenses WHERE expenseID=:expenseId")
    LiveData<Expense> getExpenseById(int expenseId);

    @Query("SELECT * FROM expenses")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE googleID=:googleID")
    LiveData<List<Expense>> getAllExpensesByGoogleId(String googleID);

    @Query("DELETE FROM expenses")
    void deleteAllExpenses();

    @Query("SELECT * FROM expenses WHERE categoryName=:catName")
    LiveData<List<Expense>> getExpensesByCategory(String catName);

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM expenses WHERE categoryName=:catName")
    Double getSumByCategory(String catName);

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM expenses")
    Double getSumTotal();

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM expenses WHERE googleID=:googleID")
    Double getSumTotalForGoogleID(String googleID);
}
