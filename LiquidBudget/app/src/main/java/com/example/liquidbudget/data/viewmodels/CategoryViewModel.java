package com.example.liquidbudget.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.repositories.CategoryRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository cRepository;
    private LiveData<List<Category>> cAllCategories;

    public CategoryViewModel(Application application) {
        super(application);
        cRepository = new CategoryRepository(application);
        cAllCategories = cRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return cAllCategories;
    }

    public List<String> getAllCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllCategoryNames();
    }

    public List<String> getAllIncomeCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllIncomeCategoryNames();
    }

    public List<String> getAllExpenseCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllExpenseCategoryNames();
    }

    public void insertCategory(Category category) {
        cRepository.insertCategory(category);
    }

    public void deleteCategory(Category category){
        cRepository.deleteCategory(category);
    }

}
