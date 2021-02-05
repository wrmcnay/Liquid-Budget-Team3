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

    @Query("SELECT * FROM categories WHERE googleID=:googleID")
    LiveData<List<Category>> getAllCategoriesByGoogleId(String googleID);

    @Query("SELECT categoryName FROM categories")
    List<String> getAllCategoryNames();

    @Query("SELECT categoryName FROM categories WHERE googleID=:googleID")
    List<String> getAllCategoryNamesByGoogleId(String googleID);

    @Query("SELECT CAST(SUM(categoryAmount) as DOUBLE) FROM categories WHERE categoryType=:catType AND googleID=:googleID")
    Double getPlannedTotalByType(String catType, String googleID);

    @Query("SELECT count(*) FROM categories WHERE googleID=:googleID")
    Integer getNumCategories(String googleID);

    @Query("SELECT categoryName FROM categories WHERE categoryType='Income'")
    List<String> getAllIncomeCategoryNames();

    @Query("SELECT categoryName FROM categories WHERE categoryType='Income' AND googleID=:googleID")
    List<String> getAllIncomeCategoryNamesByGoogleId(String googleID);

    @Query("SELECT categoryName FROM categories WHERE categoryType='Expense'")
    List<String> getAllExpenseCategoryNames();

    @Query("SELECT categoryName FROM categories WHERE categoryType='Expense' AND googleID=:googleID")
    List<String> getAllExpenseCategoryNamesByGoogleId(String googleID);

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
