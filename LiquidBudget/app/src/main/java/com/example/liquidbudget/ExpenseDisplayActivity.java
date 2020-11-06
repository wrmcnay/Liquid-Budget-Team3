package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.repositories.ExpenseRepository;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.ui.DataAdapters.ExpenseAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExpenseDisplayActivity extends AppBaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    private ExpenseViewModel expenseViewModel;
    private ExpenseRepository expenseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);


        FloatingActionButton toAdd = (FloatingActionButton) findViewById(R.id.addExpenseButton);
        toAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent addExpIntent = new Intent(ExpenseDisplayActivity.this, AddExpenseActivity.class);
                startActivityForResult(addExpIntent, MY_REQUEST_CODE);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.Expense_Recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expensesList) {
                adapter.setExpenses(expensesList);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE) {
            int expID = data.getIntExtra(AddExpenseActivity.EXTRA_EXP_ID, 1);
            String expenseName = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_NAME);
            String categoryName = data.getStringExtra(AddExpenseActivity.EXTRA_CAT_NAME);
            double amount = data.getDoubleExtra(AddExpenseActivity.EXTRA_AMOUNT, 0);

            Expense expense = new Expense(expenseName, categoryName, amount);
            expense.setExpenseID(expID);
            expenseViewModel.insert(expense);
            Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Expense not saved", Toast.LENGTH_SHORT).show();
        }
    }


}
