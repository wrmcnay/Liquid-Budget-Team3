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

    public LiveData<List<Category>> getAllCategoriesByGoogleId(String gid) throws ExecutionException, InterruptedException {
        return cRepository.getAllCategoriesByGoogleId(gid);
    }

    public List<String> getAllCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllCategoryNames();
    }

    public List<String> getAllCategoryNamesByGoogleId(String gid) throws ExecutionException, InterruptedException{
        return cRepository.getAllCategoryNamesByGoogleID(gid);
    }

    public Double getPlannedTotalByType(String catType, String gid) throws ExecutionException, InterruptedException {
        return cRepository.getPlannedTotalByType(catType, gid);
    }

    public Integer getNumCategories(String gid)throws ExecutionException, InterruptedException {
        return cRepository.getNumCategories(gid);
    }

    public List<String> getAllIncomeCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllIncomeCategoryNames();
    }

    public List<String> getAllIncomeCategoryNamesByGoogleId(String gid) throws ExecutionException, InterruptedException{
        return cRepository.getAllIncomeCategoryNamesByGoogleId(gid);
    }

    public List<String> getAllExpenseCategoryNames() throws ExecutionException, InterruptedException{
        return cRepository.getAllExpenseCategoryNames();
    }

    public List<String> getAllExpenseCategoryNamesByGoogleId(String gid) throws ExecutionException, InterruptedException{
        return cRepository.getAllExpenseCategoryNamesByGoogleId(gid);
    }

    public void insertCategory(Category category) {
        cRepository.insertCategory(category);
    }

    public void deleteCategory(Category category){
        cRepository.deleteCategory(category);
    }

}
