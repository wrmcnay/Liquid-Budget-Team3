package com.example.liquidbudget.data.Database;

import com.example.liquidbudget.data.model.Category;

import java.util.List;

import io.reactivex.Flowable;

public interface CategoryDataSourceInterface {
    Flowable<Category> getCategoryById(int catID);
    Flowable<List<Category>> getAllCategories();
    void insertCategory(Category... categories);
    void updateCategory(Category... categories);
    void deleteCategory(Category categories);
    void deleteAllCategories();
}
