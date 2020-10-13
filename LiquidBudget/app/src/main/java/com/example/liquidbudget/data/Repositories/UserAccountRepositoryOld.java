package com.example.liquidbudget.data.Repositories;

import com.example.liquidbudget.data.Entities.UserAccount;

import java.util.List;

import io.reactivex.Flowable;

public class UserAccountRepositoryOld implements UserAccountDataSourceInterface{
    private UserAccountDataSourceInterface mLocalDataSource;
    private static UserAccountRepositoryOld mInstance;

    public UserAccountRepositoryOld(UserAccountDataSourceInterface mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserAccountRepositoryOld getInstance(UserAccountDataSourceInterface mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new UserAccountRepositoryOld(mLocalDataSource);
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
