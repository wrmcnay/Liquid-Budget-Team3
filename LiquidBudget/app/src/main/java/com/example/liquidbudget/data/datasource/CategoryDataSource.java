package com.example.liquidbudget.data.datasource;

import com.example.liquidbudget.data.dao.CategoryDAO;
import com.example.liquidbudget.data.entities.Category;

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
    public Flowable<List<String>> getAllCategoryNames() {
        return categoryDAO.getAllCategoryNames();
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
