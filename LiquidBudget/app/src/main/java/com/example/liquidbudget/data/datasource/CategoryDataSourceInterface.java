package com.example.liquidbudget.data.datasource;

import com.example.liquidbudget.data.entities.Category;

import java.util.List;

import io.reactivex.Flowable;

public interface CategoryDataSourceInterface {
    Flowable<Category> getCategoryById(int catID);
    Flowable<Category> getCategoryByName(String catName);
    Flowable<List<Category>> getAllCategories();
    Flowable<List<String>> getAllCategoryNames();
    void insertCategory(Category... categories);
    void updateCategory(Category... categories);
    void deleteCategory(Category categories);
    void deleteAllCategories();
    void deleteCategoryByName(String catName);
}
