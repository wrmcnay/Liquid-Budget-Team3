package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.ExpenseDAO;
import com.example.liquidbudget.data.Database.ExpenseDatabase;
import com.example.liquidbudget.data.entities.Expense;

import java.util.List;

public class ExpenseRepository {

    private ExpenseDAO expenseDAO;
    private LiveData<List<Expense>> allExpenses;

    public ExpenseRepository(Application application) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        expenseDAO = database.expenseDAO();
        allExpenses = expenseDAO.getAllExpenses();
    }

    public void insert(final Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.insertExpense(expense);
        });
    }

    public void update(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.updateExpense(expense);
        });
    }

    public void delete(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.deleteExpense(expense);
        });
    }

    public void deleteAllExpenses(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.deleteAllExpenses();
        });
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }
}
