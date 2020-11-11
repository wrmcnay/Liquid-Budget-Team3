package com.example.liquidbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.entities.UserAccount;

import java.util.List;

@Dao
public interface UserAccountDAO {

    @Query("SELECT * FROM users WHERE userID=:uid")
    LiveData<UserAccount> getUserByID(int uid);

    @Query("SELECT * FROM users")
    LiveData<List<UserAccount>> getAllUsers();

    @Insert
    void insertUser(UserAccount... accountInfo);

    @Update
    void updateUser(UserAccount... accountInfo);

    @Delete
    void deleteUser(UserAccount accountInfo);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}