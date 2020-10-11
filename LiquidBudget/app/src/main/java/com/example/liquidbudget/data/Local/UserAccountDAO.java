package com.example.liquidbudget.data.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.model.UserAccount;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserAccountDAO {

    @Query("SELECT * FROM accountInfo WHERE userID=:uid")
    Flowable<UserAccount> getUserByID(int uid);

    @Query("SELECT * FROM accountInfo")
    Flowable<List<UserAccount>> getAllUsers();

    @Insert
    void insertUser(UserAccount... accountInfo);

    @Update
    void updateUser(UserAccount... accountInfo);

    @Delete
    void deleteUser(UserAccount accountInfo);

    @Query("DELETE FROM accountInfo")
    void deleteAllUsers();
}