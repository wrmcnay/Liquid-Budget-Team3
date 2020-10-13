package com.example.liquidbudget.data.Repositories;

import com.example.liquidbudget.data.Entities.UserAccount;

import java.util.List;

import io.reactivex.Flowable;

public interface UserAccountDataSourceInterface {
    Flowable<UserAccount> getUserByID(int uid);
    Flowable<List<UserAccount>> getAllUsers();
    void insertUser(UserAccount... accountInfo);
    void updateUser(UserAccount... accountInfo);
    void deleteUser(UserAccount accountInfo);
    void deleteAllUsers();
}
