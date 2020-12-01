package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.IncomeDAO;
import com.example.liquidbudget.data.database.IncomeDatabase;
import com.example.liquidbudget.data.entities.Income;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IncomeRepository {

    private IncomeDAO incomeDAO;
    private LiveData<List<Income>> allIncomes;
    private LiveData<List<Double>> amountByIncID;
    private Double sumByCategory;

    public IncomeRepository(Application application) {
        IncomeDatabase database = IncomeDatabase.getInstance(application);
        incomeDAO = database.incomeDAO();
        allIncomes = incomeDAO.getAllIncomes();

    }

    public void insert(final Income income) {
        IncomeDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.insertIncome(income);
        });
    }

    public void update(Income income) {
        IncomeDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.updateIncome(income);
        });
    }

    public void deleteIncome(Income income) {
        IncomeDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.deleteIncome(income);
        });
    }

    public void deleteAllIncomes(Income income) {
        IncomeDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.deleteAllIncomes();
        });
    }

    public LiveData<List<Income>> getAllIncomes() {
        return allIncomes;
    }

    public LiveData<List<Double>> getAmountByIncID(int incID) {
        IncomeDatabase.databaseWriteExecutor.execute(() -> {
            amountByIncID = incomeDAO.getAmountByIncID(incID);
        });
        return amountByIncID;
    }

    public Double getSumByCategory(String catName) throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return incomeDAO.getSumByCategory(catName);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getSumTotal() throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception{
                return incomeDAO.getSumTotal();
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<Income>> getIncomesByCategory(String catName) throws ExecutionException, InterruptedException{
        Callable<LiveData<List<Income>>> callable = new Callable<LiveData<List<Income>>>(){
            @Override
            public LiveData<List<Income>> call() throws Exception{
                return incomeDAO.getIncomesByCategory(catName);
            }
        };
        Future<LiveData<List<Income>>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

}
