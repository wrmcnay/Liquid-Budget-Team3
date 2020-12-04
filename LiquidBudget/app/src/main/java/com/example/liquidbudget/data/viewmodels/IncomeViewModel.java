package com.example.liquidbudget.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.repositories.IncomeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeViewModel extends AndroidViewModel {

    private IncomeRepository mRepository;
    private LiveData<List<Income>> mAllIncomes;

    public IncomeViewModel(Application application) {
        super(application);
        mRepository = new IncomeRepository(application);
        mAllIncomes = mRepository.getAllIncomes();
    }

    public LiveData<List<Income>> getAllIncomes() {
        return mAllIncomes;
    }

    public LiveData<List<Income>> getAllIncomesByGoogleId(String gid) throws ExecutionException, InterruptedException {
        return mRepository.getAllIncomesByGoogleId(gid);
    }

    public LiveData<List<Income>> getIncomesByCategory(String catName) throws ExecutionException, InterruptedException{
        return mRepository.getIncomesByCategory(catName);
    }

    public Double getSumByCategory(String catName) throws ExecutionException, InterruptedException{
        return mRepository.getSumByCategory(catName);
    }

    public Double getSumTotal() throws ExecutionException, InterruptedException{
        return mRepository.getSumTotal();//is this the added sum of all incomes?
    }

    public Double getSumTotalForGoogleID(String gid) throws ExecutionException, InterruptedException{
        return mRepository.getSumTotalForGoogleID(gid);//is this the added sum of all incomes?
    }

    public void insert(Income income) {
        mRepository.insert(income);
    }

    public void deleteIncome(Income income) {
        mRepository.deleteIncome(income);
    }

    public void updateIncome(Income income) {
        mRepository.update(income);
    }
}


