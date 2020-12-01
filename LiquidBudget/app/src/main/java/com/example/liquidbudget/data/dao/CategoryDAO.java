package com.example.liquidbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM categories WHERE categoryID=:catID")
    LiveData<Category> getCategoryById(int catID);

    @Query("SELECT * FROM categories WHERE categoryName=:catName LIMIT 1")
    LiveData<Category> getCategoryByName(String catName);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT categoryName FROM categories")
    List<String> getAllCategoryNames();

    @Query("SELECT categoryName FROM categories WHERE categoryType='Income'")
    List<String> getAllIncomeCategoryNames();

    @Query("SELECT categoryName FROM categories WHERE categoryType='Expense'")
    List<String> getAllExpenseCategoryNames();

    @Insert
    void insertCategory(Category... categories);

    @Update
    void updateCategory(Category... categories);

    @Delete
    void deleteCategory(Category categories);

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Query("DELETE FROM categories WHERE categoryName=:catName")
    void deleteCategoryByName(String catName);
}
