package com.example.liquidbudget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.database.CategoryDatabase;
import com.example.liquidbudget.data.datasource.CategoryDataSource;
import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.repositories.CategoryRepository;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private LiveData<List<Expense>> expenseList;
    private LiveData<List<Income>> incomeList;
    private CategoryRepository categoryRepository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        String categoryName = getIntent().getStringExtra("CategoryName");
        TextView displayName = (TextView) findViewById(R.id.category_display);
        Button deleteCategory = (Button) findViewById(R.id.deleteCategory);
        displayName.setText(categoryName);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);

        expenseList = expenseViewModel.getExpensesByCategorycatNam(categoryName);
        incomeList = incomeViewModel.getIncomesByCategory(categoryName);

        CategoryDatabase categoryDatabase = CategoryDatabase.getInstance(this); // Create Database
        categoryRepository = CategoryRepository.getInstance(CategoryDataSource.getInstance(categoryDatabase.categoryDAO()));


        deleteCategory.setOnClickListener(new View.OnClickListener(){
            private final static String REQUEST_COLOR = "";
            @Override
            public void onClick(View view){
//                Flowable<Category> categoryFlowable = categoryRepository.getCategoryByName(categoryName);
//                Iterable<Category> categoryIteratable = categoryFlowable.blockingIterable();
//                Iterator<Category> categoryIterator = categoryIteratable.iterator();
//                while(categoryIterator.hasNext()){
//                    categoryRepository.deleteCategory(categoryIterator.next());
//                }
                categoryRepository.deleteCategoryByName(categoryName);
                finish();
            }
        });

    }
}
