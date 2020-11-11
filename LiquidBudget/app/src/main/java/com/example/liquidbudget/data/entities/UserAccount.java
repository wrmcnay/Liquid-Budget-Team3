package com.example.liquidbudget.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserAccount {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    private int userID;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    public UserAccount(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringBuilder(name).append("\n").append(email).toString();
    }
}
