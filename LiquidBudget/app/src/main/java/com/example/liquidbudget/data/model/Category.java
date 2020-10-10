package com.example.liquidbudget.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @NonNull
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="categoryID")
    private int categoryID;

    @ColumnInfo(name="categoryName")
    private String categoryName;

    @ColumnInfo(name="categoryColor")
    private String categoryColor;

    public Category(int categoryID, String categoryName, String categoryColor){
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
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

    @Override
    public String toString() {
        return new StringBuilder(categoryName).append("\n").append(categoryColor).toString();
    }
}
