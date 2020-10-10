package com.example.liquidbudget.data.Database;

import com.example.liquidbudget.data.model.Category;

import java.util.List;

import io.reactivex.Flowable;

public class CategoryRepository implements CategoryDataSourceInterface{

    private CategoryDataSourceInterface mLocalDataSource;

    private static CategoryRepository mInstance;

    public CategoryRepository(CategoryDataSourceInterface mLocalDataSource){
        this.mLocalDataSource = mLocalDataSource;
    }

    public static CategoryRepository getInstance(CategoryDataSourceInterface mLocalDataSource){
        if(mInstance == null){
            mInstance = new CategoryRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<Category> getCategoryById(int catID) {
        return mLocalDataSource.getCategoryById(catID);
    }

    @Override
    public Flowable<List<Category>> getAllCategories() {
        return mLocalDataSource.getAllCategories();
    }

    @Override
    public void insertCategory(Category... categories) {
        mLocalDataSource.insertCategory(categories);
    }

    @Override
    public void updateCategory(Category... categories) {
        mLocalDataSource.updateCategory(categories);
    }

    @Override
    public void deleteCategory(Category categories) {
        mLocalDataSource.deleteCategory(categories);
    }

    @Override
    public void deleteAllCategories() {
        mLocalDataSource.deleteAllCategories();
    }
}
