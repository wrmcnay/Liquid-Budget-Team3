package com.example.liquidbudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.entities.UserAccount;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_NONE;

public class MainActivity extends AppBaseActivity implements TutorialDialogue.TutorialDialogListener {
    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CategoryViewModel categoryViewModel;
    TutorialDialogue d;
    Integer tutorialState = 0;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private SimpleDateFormat monthFormat;
    private String month;

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

        dateTimeDisplay = (TextView)findViewById(R.id.text_date_display);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(getString(R.string.homepage_date, date));

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        TextView totalIncomeTextView = findViewById(R.id.planned_income_total);
        TextView totalExpenseTextView = findViewById(R.id.planned_expense_total);
        TextView runningIncomeTextView = findViewById(R.id.running_income);
        TextView runningExpenseTextView = findViewById(R.id.running_expense);
        TextView cashLeftTextView1 = findViewById(R.id.cash_left1);
        TextView cashLeftTextView = findViewById(R.id.cash_left2);
        TextView cashLeftTextView3 = findViewById(R.id.cash_left3);

        Double totalIncome;
        Double totalExpense = null;
        Double runningIncome;
        Double runningExpense =null;
        Double cashLeft;

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

        monthFormat = new SimpleDateFormat("MM");
        month = monthFormat.format(calendar.getTime());

        try{
            runningIncomeTextView.setText("0.00");
            runningIncome = incomeViewModel.getMonthSumTotalForGoogleID(account.getId(), month);
            if(runningIncome != null){
                runningIncome = round(runningIncome, 2);
                runningIncomeTextView.setText(String.format(("%.2f"), runningIncome));
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        try{
            runningExpenseTextView.setText("0.00");
            runningExpense = expenseViewModel.getMonthSumTotalForGoogleID(account.getId(), month);

            if(runningExpense != null) {
                runningExpense = round(runningExpense, 2);
                runningExpenseTextView.setText(String.format(("%.2f"), runningExpense));
           }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
        try{
            cashLeftTextView.setText("0.00");

            if(totalExpense != null && runningExpense != null) {
                cashLeft = totalExpense - runningExpense;

                if (cashLeft<0){ //if negative, change message and make red
                    cashLeftTextView1.setText("You've spent");
                    cashLeftTextView.setTextColor(Color.parseColor("#951313"));
                    cashLeftTextView3.setText("more than planned this month.");
                    cashLeft = -cashLeft;
                }

                cashLeft = round(cashLeft, 2);
                cashLeftTextView.setText(String.format(("%.2f"), cashLeft));
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
        try {
            if(account != null)
             tutorialProgressCheck();
        } catch (ExecutionException e) {
            Log.d("ERROR", e.getLocalizedMessage());
        } catch (InterruptedException e) {
            Log.d("ERROR", e.getLocalizedMessage());
        }
    }

    private void tutorialProgressCheck() throws ExecutionException, InterruptedException {
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(userAccountViewModel.getTutorialState(account.getId()) != 0){
            Log.d("DEBUG", "TUTORIAL COMPLETE");
        } else {
            Log.d("DEBUG", "TUT INCOMPLETE");
            d = new TutorialDialogue();
            d.setCurrentLayout(R.layout.tut1);
            d.setCancelable(false);
            d.show(getSupportFragmentManager(), "TutorialDialogFragment");
            tutorialState = 1;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(tutorialState == 2){
            d.setCurrentLayout(R.layout.tut3);
            d.setPositiveButtonText("OK");
            d.setNegativeButtonText("QUIT");
            d.show(getSupportFragmentManager(), "TutorialDialogFragment");
            tutorialState = 3;
            UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            userAccountViewModel.setTutorialState(account.getId(), 1);
        }
        return super.onOptionsItemSelected(item);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(tutorialState == 1) {
            d.dismiss();
            d.setCurrentLayout(R.layout.tut2);
            d.setPositiveButtonText("OK");
            d.setNegativeButtonText("QUIT");
            d.show(getSupportFragmentManager(), "TutorialDialogFragment");
            tutorialState = 2;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        userAccountViewModel.setTutorialState(account.getId(), 10);
    }
}


