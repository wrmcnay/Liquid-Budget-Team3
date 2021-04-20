package com.example.liquidbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;

    private final static int MY_REQUEST_CODE= 1;
    private int categoryId;
    private String categoryType;
    private String categoryName;
    private double categoryAmount;
    private String googleID;

    private String newCatName = "";
    private Double newCatAmount = 0.0;
    private String newCatColor = " ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null)
            googleID = googleAccount.getId();

        Intent intent = getIntent();

        categoryId = intent.getIntExtra("CategoryId", -1);
        categoryName = intent.getStringExtra("CategoryName");
        categoryType = intent.getStringExtra("CategoryType");
        categoryAmount = intent.getDoubleExtra("CategoryAmount", 0.0);
        googleID = intent.getStringExtra("googleid");

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

        TextView iHistory = (TextView) findViewById(R.id.iHistory);
        TextView eHistory = (TextView) findViewById(R.id.ehistory);

        TextView displayName = (TextView) findViewById(R.id.category_display);
        displayName.setText(categoryName);

        TextView amountType = (TextView) findViewById(R.id.amountType);
        amountType.setText(amountText);

        TextView amountPlanned = (TextView) findViewById(R.id.amount_planned_for_category);
        amountPlanned.setText(String.format(("%.2f"), categoryAmount));

        Button editCategory = (Button) findViewById(R.id.editCategory);
        editCategory.setOnClickListener(new View.OnClickListener(){
            private final static String REQUEST_COLOR = "";
            public void onClick(View v) {
                Intent editCategory = new Intent(ViewCategoryActivity.this, EditCategoryActivity.class);
                String catName = categoryName;
                Double catAmount = categoryAmount;

                editCategory.putExtra("CategoryName", catName);
                editCategory.putExtra("CategoryAmount", catAmount);
                startActivityForResult(editCategory, MY_REQUEST_CODE);
            }
        });

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        if (googleAccount != null) {
            try {
                Double amount;
                if (categoryType.equals("Income")) {
                    amount = incomeViewModel.getSumByCategory(categoryName, googleID);
                } else if (categoryType.equals("Expense")) {
                    amount = expenseViewModel.getSumByCategory(categoryName, googleID);
                } else {
                    amount = 0.0;
                }
                TextView totalAmount = (TextView) findViewById(R.id.total_amount_for_category);
                if (amount == null) {
                    totalAmount.setText("0.00");
                } else {
                    totalAmount.setText(String.format(("%.2f"), amount));
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }


        RecyclerView incomeRecyclerView = findViewById(R.id.incomeView);
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        incomeRecyclerView.setHasFixedSize(false);

        IncomeAdapter iAdapter = new IncomeAdapter();
        incomeRecyclerView.setAdapter(iAdapter);

        RecyclerView expenseRecyclerView = findViewById(R.id.expenseView);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setHasFixedSize(false);

        ExpenseAdapter eAdapter = new ExpenseAdapter();
        expenseRecyclerView.setAdapter(eAdapter);

        if (categoryType.equals("Income")) {
            incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
            try {
                incomeViewModel.getIncomesByCategory(categoryName, googleID).observe(this, new Observer<List<Income>>() {
                    @Override
                    public void onChanged(List<Income> incomesList) {
                        iAdapter.setIncomes(incomesList);
                    }
                });
            } catch(Exception e){
                Log.e("ERROR", e.getMessage());
            }
            eHistory.setVisibility(View.GONE);
            expenseRecyclerView.setVisibility(View.GONE);

        } else if (categoryType.equals("Expense")) {
            expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
            try{
                expenseViewModel.getExpensesByCategory(categoryName, googleID).observe(this, new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(List<Expense> expensesList) {
                        eAdapter.setExpenses(expensesList);
                    }
                });
            } catch(Exception e){
                Log.e("ERROR", e.getMessage());
            }
            iHistory.setVisibility(View.GONE);
            incomeRecyclerView.setVisibility(View.GONE);
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("View Category");

        iAdapter.setOnItemClickListener(new IncomeAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Income income = iAdapter.getIncomeAtPosition(position);
                //TODO We could add interaction here but the income/expense update needs to resolve to their respect activities
            }
        });

        eAdapter.setOnItemClickListener(new ExpenseAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Expense expense = eAdapter.getExpenseAtPosition(position);
                //TODO We could add interaction here but the income/expense update needs to resolve to their respect activities
            }
        });
        
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Transactions");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode==MY_REQUEST_CODE){
            if(data != null){
                newCatName = data.getStringExtra("Name");
                newCatAmount = data.getDoubleExtra("Amount", categoryAmount);
                Category category = new Category(categoryId, newCatName, newCatAmount, categoryType, " ", googleID);
                categoryViewModel.updateCategory(category);
                if(categoryType.equals("Income")){
                    incomeViewModel.updateCategoryName(categoryName, newCatName);
                } else if (categoryType.equals("Expense")){
                    incomeViewModel.updateCategoryName(categoryName, newCatName);
                }
                finish();
                Toast.makeText(ViewCategoryActivity.this, "Category Edited", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ViewCategoryActivity.this, "Problem with categoryID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ViewCategoryActivity.this, "Category could not be edited", Toast.LENGTH_SHORT).show();
        }
    }

}
