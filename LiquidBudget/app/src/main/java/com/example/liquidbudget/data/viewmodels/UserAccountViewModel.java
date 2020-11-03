package com.example.liquidbudget.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.UserAccount;
import com.example.liquidbudget.data.repositories.UserAccountRepository;

import java.util.List;

public class UserAccountViewModel extends AndroidViewModel {

    private UserAccountRepository mRepository;
    private LiveData<List<UserAccount>> mAllUsers;

    public UserAccountViewModel(Application application) {
        super(application);
        mRepository = new UserAccountRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<UserAccount>> getAllUsers() { return mAllUsers; }

    public void insert(UserAccount userAccount){
        mRepository.insert(userAccount);
    }
}
