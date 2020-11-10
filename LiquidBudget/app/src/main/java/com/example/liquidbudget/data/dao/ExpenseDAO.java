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

    @Query("DELETE FROM expenses")
    void deleteAllExpenses();
}
