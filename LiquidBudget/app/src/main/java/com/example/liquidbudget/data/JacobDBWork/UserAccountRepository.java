package com.example.liquidbudget.data.JacobDBWork;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.JacobDBWork.UserAccountDAO;
import com.example.liquidbudget.data.JacobDBWork.UserAccountDatabase;
import com.example.liquidbudget.data.JacobDBWork.UserAccount;
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
