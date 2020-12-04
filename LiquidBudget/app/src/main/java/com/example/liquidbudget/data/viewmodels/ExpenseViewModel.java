package com.example.liquidbudget.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.repositories.ExpenseRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository eRepository;
    private LiveData<List<Expense>> eAllExpenses;

    public ExpenseViewModel(Application application) {
        super(application);
        eRepository = new ExpenseRepository(application);
        eAllExpenses = eRepository.getAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return eAllExpenses;
    }

    public LiveData<List<Expense>> getAllExpensesByGoogleID(String gid) throws ExecutionException, InterruptedException {
        return eRepository.getAllExpensesByGoogleId(gid);
    }

    public LiveData<List<Expense>> getExpensesByCategory(String catName, String gid) throws ExecutionException, InterruptedException{
        return eRepository.getExpensesByCategory(catName, gid);
    }

    public Double getSumByCategory(String catName, String gid) throws ExecutionException, InterruptedException{
        return eRepository.getSumByCategory(catName, gid);
    }

    public Double getSumTotal() throws ExecutionException, InterruptedException{
        return eRepository.getSumTotal();// is this the sum of all expenses?
    }

    public void insert(Expense expense) {
        eRepository.insert(expense);
    }

    public void deleteExpense(Expense expense) {
        eRepository.deleteExpense(expense);
    }

    public void updateExpense(Expense expense) {
        eRepository.update(expense);
    }

    public Double getSumTotalForGoogleID(String gid) throws ExecutionException, InterruptedException{
        return eRepository.getSumTotalForGoogleID(gid);//is this the added sum of all incomes?
    }

}
