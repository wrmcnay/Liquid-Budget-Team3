package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.ui.DataAdapters.ExpenseAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExpenseDisplayActivity extends AppBaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    private static final int UPDATE_EXPENSE_ACTIVITY_REQUEST_CODE = 2;
    private ExpenseViewModel expenseViewModel;

    public static final String EXTRA_DATA_UPDATE_NAME = "extra_expense_name_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY = "extra_expense_category_to_update";
    public static final String EXTRA_DATA_UPDATE_AMOUNT = "extra_expense_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_ID = "extra_data_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);

        FloatingActionButton addExpenseBtn = (FloatingActionButton) findViewById(R.id.addExpenseButton);
        addExpenseBtn.setOnClickListener(new View.OnClickListener(){
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

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Expense myExpense = adapter.getExpenseAtPosition(position);
                        Toast.makeText(ExpenseDisplayActivity.this, "Deleting " +
                                myExpense.getExpenseName(), Toast.LENGTH_LONG).show();

                        // Delete the expense
                        expenseViewModel.deleteExpense(myExpense);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExpenseAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Expense expense = adapter.getExpenseAtPosition(position);
                launchUpdateExpenseActivity(expense);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE) {
            int expID;
            String expenseName, categoryName;
            double amount;
            if(data != null) {
                expenseName = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_NAME);
                categoryName = data.getStringExtra(AddExpenseActivity.EXTRA_CAT_NAME);
                amount = data.getDoubleExtra(AddExpenseActivity.EXTRA_AMOUNT, 0);
                Expense expense = new Expense(expenseName, categoryName, amount);
                expenseViewModel.insert(expense);
                Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error. Expense not saved", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Expense not saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchUpdateExpenseActivity(Expense expense) {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_NAME, expense.getExpenseName());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY, expense.getCategoryName());
        intent.putExtra(EXTRA_DATA_UPDATE_AMOUNT, expense.getAmount());
        intent.putExtra(EXTRA_DATA_UPDATE_ID, expense.getExpenseID());
        startActivityForResult(intent, UPDATE_EXPENSE_ACTIVITY_REQUEST_CODE);
    }

}
