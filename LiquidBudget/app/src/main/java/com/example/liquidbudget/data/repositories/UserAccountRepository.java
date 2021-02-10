package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.UserAccount;
import com.example.liquidbudget.data.dao.UserAccountDAO;
import com.example.liquidbudget.data.database.UserAccountDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public Boolean getUserByGoogleId(String gid) throws ExecutionException, InterruptedException {
        Callable<Boolean> callable = new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return userAccountDAO.userExistsByGId(gid);
            }
        };
        Future<Boolean> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public int getTutorialState(String gid) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                return userAccountDAO.getTutorialState(gid);
            }
        };
        Future<Integer> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void setTutorialState(String gid, int state){
        UserAccountDatabase.databaseWriteExecutor.execute(() -> {
            userAccountDAO.setTutorialState(gid, state);
        });
    }
}
