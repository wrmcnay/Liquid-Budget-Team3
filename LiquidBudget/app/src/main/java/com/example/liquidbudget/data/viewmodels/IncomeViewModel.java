package com.example.liquidbudget.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.repositories.IncomeRepository;

import java.util.List;

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

    public void insert(Income income) {
        mRepository.insert(income);
    }

    public void deleteIncome(Income income) {
        mRepository.deleteIncome(income);
    }
}


