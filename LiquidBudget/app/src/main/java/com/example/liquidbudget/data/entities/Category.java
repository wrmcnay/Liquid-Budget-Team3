package com.example.liquidbudget.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @NonNull
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="categoryID")
    private int categoryID;

    @ColumnInfo(name="categoryName")
    private String categoryName;

    // 'Expense' for Expense, 'Income' for Income
    @ColumnInfo(name="categoryType")
    private String categoryType;

    @ColumnInfo(name="categoryAmount")
    private Double categoryAmount;

    @ColumnInfo(name="categoryColor")
    private String categoryColor;

    @ColumnInfo(name="googleID")
    private String googleID;


    public Category(String categoryName, Double categoryAmount, String categoryType, String categoryColor, String googleID){
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.categoryAmount = categoryAmount;
        this.categoryColor = categoryColor;
        this.googleID = googleID;
    }

    @Ignore
    public Category(int categoryID, String categoryName,  Double categoryAmount, String categoryType, String categoryColor, String googleID){
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.categoryAmount = categoryAmount;
        this.categoryColor = categoryColor;
        this.googleID = googleID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getCategoryType(){ return categoryType; }

    public void setCategoryType(String categoryType){ this.categoryType = categoryType; }

    public Double getCategoryAmount(){return categoryAmount; }

    public void setCategoryAmount(Double categoryAmount){ this.categoryAmount = categoryAmount;}

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
