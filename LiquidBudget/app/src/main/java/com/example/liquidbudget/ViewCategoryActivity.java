package com.example.liquidbudget;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.ExpenseAdapter;
import com.example.liquidbudget.ui.DataAdapters.IncomeAdapter;

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

        try{
            Double incomeDouble = incomeViewModel.getSumByCategory(categoryName);
            TextView incomeTotal = (TextView) findViewById(R.id.income_total);
            incomeTotal.setText(""+incomeDouble);

            Double expenseDouble = expenseViewModel.getSumByCategory(categoryName);
            TextView expenseTotal = (TextView) findViewById(R.id.expense_total);
            expenseTotal.setText("" + expenseDouble);
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        RecyclerView incomeRecyclerView = findViewById(R.id.incomeView);
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        incomeRecyclerView.setHasFixedSize(false);

        IncomeAdapter iAdapter = new IncomeAdapter();
        incomeRecyclerView.setAdapter(iAdapter);

        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        try {
            incomeViewModel.getIncomesByCategory(categoryName).observe(this, new Observer<List<Income>>() {
                @Override
                public void onChanged(List<Income> incomesList) {
                    iAdapter.setIncomes(incomesList);
                }
            });
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }


        RecyclerView expenseRecyclerView = findViewById(R.id.expenseView);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setHasFixedSize(false);

        ExpenseAdapter eAdapter = new ExpenseAdapter();
        expenseRecyclerView.setAdapter(eAdapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        try{
            expenseViewModel.getExpensesByCategory(categoryName).observe(this, new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expensesList) {
                    eAdapter.setExpenses(expensesList);
                }
            });
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

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
