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
    private long userID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name="googleId")
    public String googleId;

    @ColumnInfo(name="tutorialState")
    private int tutorialState;

    @ColumnInfo(name="receiveWeeklyNotifs")
    private boolean receiveWeeklyNotifs;

    public UserAccount(String name, String email, String googleId, int tutorialState, boolean receiveWeeklyNotifs) {
        this.name = name;
        this.email = email;
        this.googleId = googleId;
        this.tutorialState = tutorialState;
        this.receiveWeeklyNotifs = receiveWeeklyNotifs;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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

    public String getGoogleId() { return googleId; }

    public void setGoogleId(String gId) { this.googleId = gId; }

    public void setTutorialState(int state){
        tutorialState = state;
    }

    public int getTutorialState() { return tutorialState; }

    public boolean getReceiveWeeklyNotifs() { return receiveWeeklyNotifs; }

    public void setReceiveWeeklyNotifs(boolean state) { receiveWeeklyNotifs = state; }

    @Override
    public String toString() {
        return new StringBuilder(name).append("\n").append(email).toString();
    }
}
