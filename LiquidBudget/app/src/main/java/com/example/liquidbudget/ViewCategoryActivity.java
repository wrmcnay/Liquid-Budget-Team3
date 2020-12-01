package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.ExpenseAdapter;
import com.example.liquidbudget.ui.DataAdapters.IncomeAdapter;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_UPDATE_CATEGORY_NAME = "extra_category_name_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_TYPE = "extra_category_type_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_AMOUNT = "extra_category_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_ID = "extra_data_cat_id";

    private final static int UPDATE_CATEGORY_ACTIVITY_REQUEST_CODE = 2;

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;

    private LiveData<List<Expense>> expenseList;
    private LiveData<List<Income>> incomeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        String categoryName = getIntent().getStringExtra("CategoryName");
        String categoryType = getIntent().getStringExtra("CategoryType");
        Double categoryAmount = getIntent().getDoubleExtra("CategoryAmount", 0.0);

        String amountText = "None";
        if (categoryType != null && !categoryType.isEmpty()) {
            if (categoryType.equals("Income")) {
                amountText = "Amount Saved";
            } else if (categoryType.equals("Expense")) {
                amountText = "Amount Spent";
            }
        } else {
            amountText = "No Type";
        }

        TextView displayName = (TextView) findViewById(R.id.category_display);
        displayName.setText(categoryName);

        TextView amountType = (TextView) findViewById(R.id.amountType);
        amountType.setText(amountText);

        TextView amountPlanned = (TextView) findViewById(R.id.amount_planned_for_category);
        amountPlanned.setText("" + categoryAmount);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        Button editCategory = (Button) findViewById(R.id.editCategory);

        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Category category = categoryViewModel.getCategoryByName(categoryName);
                    launchUpdateCategoryActivity(category);
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

//        expenseList = expenseViewModel.getExpensesByCategory(categoryName);
//        incomeList = incomeViewModel.getIncomesByCategory(categoryName);

        try{
            Double amount;
            if (categoryType.equals("Income")){
                amount = incomeViewModel.getSumByCategory(categoryName);
            } else if (categoryType.equals("Expense")){
                amount = expenseViewModel.getSumByCategory(categoryName);
            } else {
                amount = 0.0;
            }
            TextView totalAmount = (TextView) findViewById(R.id.total_amount_for_category);
            if(amount == 0.0) {
                totalAmount.setText("0");
            } else {
                totalAmount.setText("" + amount);
            }
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

    public void launchUpdateCategoryActivity(Category category) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_NAME, category.getCategoryName());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_AMOUNT, category.getCategoryAmount());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_TYPE, category.getCategoryType());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_ID, category.getCategoryID());
        startActivityForResult(intent, UPDATE_CATEGORY_ACTIVITY_REQUEST_CODE);
    }
}
