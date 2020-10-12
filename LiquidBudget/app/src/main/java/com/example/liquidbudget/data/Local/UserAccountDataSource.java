package com.example.liquidbudget.data.Local;

import com.example.liquidbudget.data.Database.UserAccountDataSourceInterface;
import com.example.liquidbudget.data.model.UserAccount;

import java.util.List;

import io.reactivex.Flowable;

public class UserAccountDataSource implements UserAccountDataSourceInterface {
    private UserAccountDAO userAccountDAO;
    private static UserAccountDataSource mInstance;

    public UserAccountDataSource(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public static UserAccountDataSource getInstance(UserAccountDAO userAccountDAO) {
        if (mInstance == null) {
            mInstance = new UserAccountDataSource(userAccountDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<UserAccount> getUserByID(int uid) {
        return userAccountDAO.getUserByID(uid);
    }

    @Override
    public Flowable<List<UserAccount>> getAllUsers() {
        return userAccountDAO.getAllUsers();
    }

    @Override
    public void insertUser(UserAccount... accountInfo) {
        userAccountDAO.insertUser(accountInfo);
    }

    @Override
    public void updateUser(UserAccount... accountInfo) {
        userAccountDAO.updateUser(accountInfo);
    }

    @Override
    public void deleteUser(UserAccount accountInfo) {
        userAccountDAO.deleteUser(accountInfo);
    }

    @Override
    public void deleteAllUsers() {
        userAccountDAO.deleteAllUsers();
    }
}
