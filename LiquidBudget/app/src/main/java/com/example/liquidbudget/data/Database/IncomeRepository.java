package com.example.liquidbudget.data.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.Local.IncomeDAO;
import com.example.liquidbudget.data.Local.IncomeDatabase;
import com.example.liquidbudget.data.model.Income;


import java.util.List;

public class IncomeRepository {

    private IncomeDAO incomeDAO;
    private LiveData<List<Income>> allIncomes;

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

    public void delete(Income income) {
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
}
