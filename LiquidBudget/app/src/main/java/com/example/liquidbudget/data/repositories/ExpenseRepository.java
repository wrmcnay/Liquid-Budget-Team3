package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.ExpenseDAO;
import com.example.liquidbudget.data.database.ExpenseDatabase;
import com.example.liquidbudget.data.database.IncomeDatabase;
import com.example.liquidbudget.data.entities.Expense;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExpenseRepository {

    private ExpenseDAO expenseDAO;
    private LiveData<List<Expense>> allExpenses;
    private Double sumByCategory;

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

    public void updateCategoryName(String newName, String oldName) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.updateCategoryName(newName, oldName);
        });
    }

    public void update(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.updateExpense(expense);
        });
    }

    public void deleteExpense(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.deleteExpense(expense);
        });
    }

    public void deleteAllExpenses(Expense expense) {
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
            expenseDAO.deleteAllExpenses();
        });
    }

    public LiveData<List<Expense>> getExpensesByCategory(String catName, String gid) throws ExecutionException, InterruptedException{
        Callable<LiveData<List<Expense>>> callable = new Callable<LiveData<List<Expense>>>(){
            @Override
            public LiveData<List<Expense>> call() throws Exception{
                return expenseDAO.getExpensesByCategory(catName, gid);
            }
        };
        Future<LiveData<List<Expense>>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public LiveData<List<Expense>> getAllExpensesByGoogleId(String gid) throws ExecutionException, InterruptedException {
        Callable<LiveData<List<Expense>>> callable = new Callable<LiveData<List<Expense>>>(){
            @Override
            public LiveData<List<Expense>> call() throws Exception{
                return expenseDAO.getAllExpensesByGoogleId(gid);
            }
        };
        Future<LiveData<List<Expense>>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getSumByCategory(String catName, String gid) throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return expenseDAO.getSumByCategory(catName, gid);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getSumTotal() throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return expenseDAO.getSumTotal();
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getSumTotalForGoogleID(String gid) throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return expenseDAO.getSumTotalForGoogleID(gid);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getMonthSumTotalForGoogleID(String gid, String month) throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return expenseDAO.getMonthSumTotalForGoogleID(gid, month);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
