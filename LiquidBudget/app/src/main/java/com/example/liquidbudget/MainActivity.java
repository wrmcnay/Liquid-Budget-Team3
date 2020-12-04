package com.example.liquidbudget;

import android.os.Bundle;
import android.text.InputFilter;
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
            TextView homepagehello = findViewById(R.id.homepageHello);
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
            totalIncomeTextView.setText("0.00");
            totalIncome = categoryViewModel.getPlannedTotalByType("Income", account.getId());

            if(totalIncome != null){
                totalIncome = round(totalIncome, 2);
                totalIncomeTextView.setText(String.format(("%.2f"), totalIncome));
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            totalExpenseTextView.setText("0.00");
            totalExpense = categoryViewModel.getPlannedTotalByType("Expense", account.getId());
            if(totalExpense != null){
                totalExpense  = round(totalExpense, 2);
                totalExpenseTextView.setText(String.format(("%.2f"), totalExpense));
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            runningIncomeTextView.setText("0.00");
            runningIncome = incomeViewModel.getSumTotalForGoogleID(account.getId());
            if(runningIncome != null){
                runningIncome = round(runningIncome, 2);
                runningIncomeTextView.setText(String.format(("%.2f"), runningIncome));
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            runningExpenseTextView.setText("0.00");
            runningExpense = expenseViewModel.getSumTotalForGoogleID(account.getId());

            if(runningExpense != null) {
                runningExpense = round(runningExpense, 2);
                runningExpenseTextView.setText(String.format(("%.2f"), runningExpense));
           }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
