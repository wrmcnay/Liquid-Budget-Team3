package com.example.liquidbudget.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.entities.Category;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM categories WHERE categoryID=:catID")
    Flowable<Category> getCategoryById(int catID);

    @Query("SELECT * FROM categories")
    Flowable<List<Category>> getAllCategories();

    @Query("SELECT categoryName FROM categories")
    Flowable<List<String>> getAllCategoryNames();

    @Insert
    void insertCategory(Category... categories);

    @Update
    void updateCategory(Category... categories);

    @Delete
    void deleteCategory(Category categories);

    @Query("DELETE FROM categories")
    void deleteAllCategories();
}
