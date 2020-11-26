package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.CategoryDAO;
import com.example.liquidbudget.data.database.CategoryDatabase;
import com.example.liquidbudget.data.entities.Category;

import java.util.List;

public class CategoryRepository{

    private CategoryDAO categoryDAO;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        CategoryDatabase database = CategoryDatabase.getInstance(application);
        categoryDAO = database.categoryDAO();
        allCategories = categoryDAO.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int catID) {
        return categoryDAO.getCategoryById(catID);
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<String>> getAllCategoryNames() {
        return categoryDAO.getAllCategoryNames();
    }

    public void insertCategory(Category... categories) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.insertCategory(categories);
        });
    }

    public void updateCategory(Category category) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.updateCategory(category);
        });
    }

    public void deleteCategory(Category categories) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.deleteCategory(categories);
        });
    }

    public void deleteAllCategories() {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.deleteAllCategories();
        });
    }
}
