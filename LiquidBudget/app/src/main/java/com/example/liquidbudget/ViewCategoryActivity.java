package com.example.liquidbudget;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;

    private LiveData<List<Expense>> expenseList;
    private LiveData<List<Income>> incomeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        String categoryName = getIntent().getStringExtra("CategoryName");
        TextView displayName = (TextView) findViewById(R.id.category_display);
        Button deleteCategory = (Button) findViewById(R.id.deleteCategory);
        displayName.setText(categoryName);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

//        expenseList = expenseViewModel.getExpensesByCategory(categoryName);
//        incomeList = incomeViewModel.getIncomesByCategory(categoryName);

        Double incomeDouble = incomeViewModel.getSumByCategory(categoryName);
        TextView incomeTotal = (TextView) findViewById(R.id.income_total);
        incomeTotal.setText(""+incomeDouble);

        Double expenseDouble = expenseViewModel.getSumByCategory(categoryName);
        TextView expenseTotal = (TextView) findViewById(R.id.expense_total);
        expenseTotal.setText(""+expenseDouble);

//        deleteCategory.setOnClickListener(new View.OnClickListener(){
//            private final static String REQUEST_COLOR = "";
//            @Override
//            public void onClick(View view){
//                Flowable<Category> categoryFlowable = categoryRepository.getCategoryByName(categoryName);
//                Iterable<Category> categoryIteratable = categoryFlowable.blockingIterable();
//                Iterator<Category> categoryIterator = categoryIteratable.iterator();
//                while(categoryIterator.hasNext()){
//                    categoryRepository.deleteCategory(categoryIterator.next());
//                }
//                finish();
//            }
//        });

    }
}
