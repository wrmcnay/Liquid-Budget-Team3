package com.example.liquidbudget.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "income")
public class Income {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "incomeID")
    private int incomeID;

    @ColumnInfo(name = "categoryID")
    private int categoryID;

    @ColumnInfo(name = "incomeName")
    private String incomeName;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "recurring")
    private boolean recurring;

    @ColumnInfo(name = "numberOf")
    private int numberOf;

    @ColumnInfo(name = "period")
    private int period;

    @ColumnInfo(name = "stable")
    private boolean stable;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "userID")
    private int userID;

    public Income(int incomeID, int categoryID, String incomeName, double amount, boolean recurring, int numberOf, int period, boolean stable, String date, int userID) {
        this.incomeID = incomeID;
        this.categoryID = categoryID;
        this.incomeName = incomeName;
        this.amount = amount;
        this.recurring = recurring;
        this.numberOf = numberOf;
        this.period = period;
        this.stable = stable;
        this.date = date;
        this.userID = userID;
    }

    public int getIncomeID() { return incomeID; }

    public void setIncomeID(int incomeID) { this.incomeID = incomeID; }

    public int getCategoryID() { return categoryID; }

    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }

    public String getName() { return incomeName; }

    public void setName(String name) { this.incomeName = name; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public boolean isRecurring() { return recurring; }

    public void setRecurring(boolean recurring) { this.recurring = recurring; }

    public int getNumberOf() { return numberOf; }

    public void setNumberOf(int numberOf) { this.numberOf = numberOf; }

    public int getPeriod() { return period; }

    public void setPeriod(int period) { this.period = period; }

    public boolean isStable() { return stable; }

    public void setStable(boolean stable) { this.stable = stable; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    @Override
    public String toString() {
        return new StringBuilder(incomeID).append("\n").append(incomeName).toString();
    }
}


