package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.IncomeDAO;
import com.example.liquidbudget.data.database.IncomeDatabase;
import com.example.liquidbudget.data.entities.Income;


import java.util.List;

public class IncomeRepository {

    private IncomeDAO incomeDAO;
    private LiveData<List<Income>> allIncomes;
    private LiveData<List<Double>> amountByIncID;

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
}
