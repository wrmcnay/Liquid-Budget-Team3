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

    @Query("SELECT * FROM users WHERE googleId == :gid")
    LiveData<UserAccount> getUserByGoogleId(String gid);

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE googleId = :gid)")
    Boolean userExistsByGId(String gid);

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

    @Query("SELECT tutorialState FROM users WHERE googleId == :gid")
    int getTutorialState(String gid);

    @Query("UPDATE users SET tutorialState=:state WHERE googleId == :gid")
    void setTutorialState(String gid, int state);

    @Query("SELECT receiveWeeklyNotifs FROM users WHERE googleId == :gid")
    boolean getReceiveWeeklyNotifs(String gid);

    @Query("UPDATE users SET receiveWeeklyNotifs=:state WHERE googleId == :gid")
    void setReceiveWeeklyNotifs(String gid, boolean state);
}