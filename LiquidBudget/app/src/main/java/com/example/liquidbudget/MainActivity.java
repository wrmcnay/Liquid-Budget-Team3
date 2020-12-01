package com.example.liquidbudget;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppBaseActivity {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String userName = account.getDisplayName();
            TextView homepagehello = findViewById(R.id.homepagehello);
            homepagehello.setText(getString(R.string.homepage_hello, userName));
        }

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        TextView totalIncomeTextView = findViewById(R.id.planned_income_total);
        TextView totalExpenseTextView = findViewById(R.id.planned_expense_total);
        TextView runningIncomeTextView = findViewById(R.id.running_income);
        TextView runningExpenseTextView = findViewById(R.id.running_expense);

        Double totalIncome;
        Double totalExpense;
        Double runningIncome;
        Double runningExpense;
        try{
            totalIncomeTextView.setText("0");
            totalIncome = categoryViewModel.getPlannedTotalByType("Income");
            if(totalIncome != 0){
                totalIncomeTextView.setText(""+totalIncome);
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            totalExpenseTextView.setText("0");
            totalExpense = categoryViewModel.getPlannedTotalByType("Expense");
            if(totalExpense != 0){
                totalExpenseTextView.setText(""+totalExpense);
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            runningIncomeTextView.setText("0");
            runningIncome = incomeViewModel.getSumTotal();
            if(runningIncome != 0){
                runningIncomeTextView.setText(""+runningIncome);
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            runningExpenseTextView.setText("0");
            runningExpense = expenseViewModel.getSumTotal();
            if(runningExpense != 0){
                runningExpenseTextView.setText(""+runningExpense);
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

    }
}
