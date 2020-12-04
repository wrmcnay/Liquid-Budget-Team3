package com.example.liquidbudget.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "expenses")
public class Expense {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expenseID")
    private int expenseID;

    @NonNull
    @ColumnInfo(name = "categoryID")
    private int categoryID;

    @NonNull
    @ColumnInfo(name = "expenseName")
    private String expenseName;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "recurringBool")
    private boolean recurringBool;

    @ColumnInfo(name = "numberOf")
    private int numberOf;

    @ColumnInfo(name = "period")
    private int period;

    @ColumnInfo(name = "stable")
    private boolean stable;

    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "userID")
    private int userID;

    @ColumnInfo(name = "googleID")
    private String googleID;

    public Expense(String expenseName, String categoryName, double amount, String googleID) {
        this.expenseName = expenseName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.googleID = googleID;
    }

    @Ignore
    public Expense(String expenseName, String categoryName, double amount, String date, String googleID) {
        this.expenseName = expenseName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.date = date;
        this.googleID = googleID;
    }

    @Ignore
    public Expense(int expenseID, String expenseName, String categoryName, double amount, String date, String googleID) {
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.date = date;
        this.googleID = googleID;
    }

    @Ignore
    public Expense(int expenseID, String expenseName, String categoryName, double amount, String googleID) {
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.googleID = googleID;
    }

    @Ignore
    public Expense(int categoryID, String expenseName, String categoryName, double amount, boolean recurringBool, int numberOf, int period, boolean stable, String date, int userID, String googleID) {
        this.categoryID = categoryID;
        this.expenseName = expenseName;
        this.categoryName = categoryName;
        this.amount = amount;
        this.recurringBool = recurringBool;
        this.numberOf = numberOf;
        this.period = period;
        this.stable = stable;
        this.date = date;
        this.userID = userID;
        this.googleID = googleID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isRecurringBool() {
        return recurringBool;
    }

    public void setRecurringBool(boolean recurringBool) {
        this.recurringBool = recurringBool;
    }

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String userID) {
        this.googleID = googleID;
    }

    @Override
    public String toString() {
        return new StringBuilder(expenseName).append("\n").append(categoryName).append("\n").append(amount).toString();
    }
}


