package com.example.liquidbudget.data.Database;

import com.example.liquidbudget.data.model.UserAccount;

import java.util.List;

import io.reactivex.Flowable;

public class UserAccountRepository implements UserAccountDataSourceInterface{
    private UserAccountDataSourceInterface mLocalDataSource;
    private static UserAccountRepository mInstance;

    public UserAccountRepository(UserAccountDataSourceInterface mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserAccountRepository getInstance(UserAccountDataSourceInterface mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new UserAccountRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<UserAccount> getUserByID(int uid) {
        return mLocalDataSource.getUserByID(uid);
    }

    @Override
    public Flowable<List<UserAccount>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertUser(UserAccount... accountInfo) {
        mLocalDataSource.insertUser(accountInfo);
    }

    @Override
    public void updateUser(UserAccount... accountInfo) {
        mLocalDataSource.updateUser(accountInfo);
    }

    @Override
    public void deleteUser(UserAccount accountInfo) {
        mLocalDataSource.deleteUser(accountInfo);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }
}
