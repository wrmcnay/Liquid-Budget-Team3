package com.example.liquidbudget.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.dao.CategoryDAO;
import com.example.liquidbudget.data.database.CategoryDatabase;
import com.example.liquidbudget.data.entities.Category;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public List<String> getAllCategoryNames() throws ExecutionException, InterruptedException{
        Callable<List<String>> callable = new Callable<List<String>>(){
            @Override
            public List<String> call() throws Exception {
                return categoryDAO.getAllCategoryNames();
            }
        };
        Future<List<String>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public Double getPlannedTotalByType(String catType) throws ExecutionException, InterruptedException{
        Callable<Double> callable = new Callable<Double>(){
            @Override
            public Double call() throws Exception {
                return categoryDAO.getPlannedTotalByType(catType);
            }
        };
        Future<Double> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
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
