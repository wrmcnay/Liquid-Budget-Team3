package com.example.liquidbudget;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.ui.DataAdapters.ExpenseAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExpenseDisplayActivity extends AppBaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    private static final int UPDATE_EXPENSE_ACTIVITY_REQUEST_CODE = 2;
    private ExpenseViewModel expenseViewModel;
    private String googleID;
    private BottomNavigationView bottomNavigationView;

    public static final String EXTRA_DATA_UPDATE_EXPENSE_NAME = "extra_expense_name_to_update";
    public static final String EXTRA_DATA_UPDATE_EXPENSE_CATEGORY = "extra_expense_category_to_update";
    public static final String EXTRA_DATA_UPDATE_EXPENSE_AMOUNT = "extra_expense_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_EXPENSE_DATE = "extra_expense_date_to_update";
    public static final String EXTRA_DATA_UPDATE_EXPENSE_ID = "extra_data_exp_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(3);
        item.setChecked(true);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();

        Button incomesButton = (Button) findViewById(R.id.incomes_button);
        Button expensesButton = (Button) findViewById(R.id.expenses_button);

        //incomesButton.setVisibility(View.VISIBLE);
        //incomesButton.setBackgroundColor(Color.TRANSPARENT);
        //expensesButton.setVisibility(View.VISIBLE);
        //expensesButton.setBackgroundColor(Color.TRANSPARENT);

        expensesButton.setClickable(false);

        incomesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incomesPage = new Intent(ExpenseDisplayActivity.this, IncomeDisplayActivity.class);
                startActivity(incomesPage);
                overridePendingTransition(0,0);
            }
        });

        FloatingActionButton addExpenseBtn = (FloatingActionButton) findViewById(R.id.add_expense_button);
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
        if(googleAccount != null) {
            try {
                expenseViewModel.getAllExpensesByGoogleID(googleID).observe(this, new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(List<Expense> expensesList) {
                        adapter.setExpenses(expensesList);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Transactions");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE && data != null) {
            String expenseName = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_NAME);
            String categoryName = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_CAT_NAME);
            double amount = data.getDoubleExtra(AddExpenseActivity.EXTRA_EXP_AMOUNT, 0);
            String date = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_DATE);

            Expense expense = new Expense(expenseName, categoryName, amount, date, googleID);
            expenseViewModel.insert(expense);
            Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
            }
        else if(resultCode == RESULT_OK && requestCode == UPDATE_EXPENSE_ACTIVITY_REQUEST_CODE && data != null) {
            String updateName = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_NAME);
            String updateCategory = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_CAT_NAME);
            double updateAmount = data.getDoubleExtra(AddExpenseActivity.EXTRA_EXP_AMOUNT, 0);
            String updateDate = data.getStringExtra(AddExpenseActivity.EXTRA_EXP_DATE);

            int id = data.getIntExtra(AddExpenseActivity.EXTRA_UPDATE_EXPENSE_ID, -1);

            if(id != -1) {
                expenseViewModel.updateExpense(new Expense(id, updateName, updateCategory, updateAmount, updateDate, googleID));
            }
            else {
                Toast.makeText(this, "Expense not able to update", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Expense not saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchUpdateExpenseActivity(Expense expense) {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_NAME, expense.getExpenseName());
        intent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_CATEGORY, expense.getCategoryName());
        intent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_AMOUNT, expense.getAmount());
        intent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_DATE, expense.getDate());
        intent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_ID, expense.getExpenseID());
        intent.putExtra("googleid", expense.getGoogleID());
        startActivityForResult(intent, UPDATE_EXPENSE_ACTIVITY_REQUEST_CODE);
    }
}
