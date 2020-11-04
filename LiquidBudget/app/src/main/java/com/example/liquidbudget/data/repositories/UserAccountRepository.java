package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.UserAccount;
import com.example.liquidbudget.data.dao.UserAccountDAO;
import com.example.liquidbudget.data.database.UserAccountDatabase;

import java.util.List;

public class UserAccountRepository {
    private UserAccountDAO userAccountDAO;
    private LiveData<List<UserAccount>> allUsers;

    public UserAccountRepository(Application application) {
        UserAccountDatabase database = UserAccountDatabase.getInstance(application);
        userAccountDAO = database.userAccountDAO();
        allUsers = userAccountDAO.getAllUsers();
    }

    public void insert(final UserAccount user){
        UserAccountDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.insertUser(user);
        });
    }

    public void update(UserAccount user){
        UserAccountDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.updateUser(user);
        });
    }

    public void delete(UserAccount user){
        UserAccountDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.deleteUser(user);
        });
    }

    public void deleteAllUsers(UserAccount user){
        UserAccountDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.deleteAllUsers();
        });
    }

    public LiveData<List<UserAccount>> getAllUsers(){
        return allUsers;
    }
}
