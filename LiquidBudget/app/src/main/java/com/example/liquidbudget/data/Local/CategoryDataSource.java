package com.example.liquidbudget.data.Local;

import com.example.liquidbudget.data.Database.CategoryDataSourceInterface;
import com.example.liquidbudget.data.model.Category;

import java.util.List;

import io.reactivex.Flowable;

public class CategoryDataSource implements CategoryDataSourceInterface{

    private CategoryDAO categoryDAO;
    private static CategoryDataSource mInstance;

    public CategoryDataSource(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public static CategoryDataSource getInstance(CategoryDAO categoryDAO){
        if(mInstance==null){
            mInstance = new CategoryDataSource(categoryDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<Category> getCategoryById(int catID) {
        return categoryDAO.getCategoryById(catID);
    }

    @Override
    public Flowable<List<Category>> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public void insertCategory(Category... categories) {
        categoryDAO.insertCategory(categories);
    }

    @Override
    public void updateCategory(Category... categories) {
        categoryDAO.updateCategory(categories);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryDAO.deleteCategory(category);
    }

    @Override
    public void deleteAllCategories() {
        categoryDAO.deleteAllCategories();
    }
}
