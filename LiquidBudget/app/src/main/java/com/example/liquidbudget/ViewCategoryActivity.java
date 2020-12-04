package com.example.liquidbudget;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;

    private String googleID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null)
            googleID = googleAccount.getId();

        String categoryName = getIntent().getStringExtra("CategoryName");
        String categoryType = getIntent().getStringExtra("CategoryType");
        Double categoryAmount = getIntent().getDoubleExtra("CategoryAmount", 0.0);
        String googleID = getIntent().getStringExtra("googleid");

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

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

//        expenseList = expenseViewModel.getExpensesByCategory(categoryName);
//        incomeList = incomeViewModel.getIncomesByCategory(categoryName);

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
            incomeRecyclerView.setVisibility(View.GONE);

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
