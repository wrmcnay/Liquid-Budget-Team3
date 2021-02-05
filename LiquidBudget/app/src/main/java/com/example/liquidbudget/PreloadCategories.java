package com.example.liquidbudget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class PreloadCategories extends AppBaseActivity {
    private CategoryViewModel categoryViewModel;
    private String googleID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preload_categories);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null)
            googleID = googleAccount.getId();

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        Button presetStudent = findViewById(R.id.presetStudent);
        presetStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.insertCategory(new Category("School-Related", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Work", 2000.0, "Income", "", googleID));
                categoryViewModel.insertCategory(new Category("Dining", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Rent", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Savings", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Misc.", 100.0, "Expense", "", googleID));
                finish();
                return;
            }
        });

        Button presetFreelancer = findViewById(R.id.presetFreelancer);
        presetFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.insertCategory(new Category("Groceries", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Work", 2000.0, "Income", "", googleID));
                categoryViewModel.insertCategory(new Category("Dining", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Rent", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Savings", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Misc.", 100.0, "Expense", "", googleID));
                finish();
                return;
            }
        });

        Button presetFulltime = findViewById(R.id.presetFulltime);
        presetFulltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.insertCategory(new Category("Groceries", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Work", 2000.0, "Income", "", googleID));
                categoryViewModel.insertCategory(new Category("Dining", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Rent", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Savings", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Misc.", 100.0, "Expense", "", googleID));
                finish();
                return;
            }
        });

        Button presetBusiness = findViewById(R.id.presetBusiness);
        presetBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryViewModel.insertCategory(new Category("Groceries", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Work", 2000.0, "Income", "", googleID));
                categoryViewModel.insertCategory(new Category("Dining", 100.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Rent", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Savings", 500.0, "Expense", "", googleID));
                categoryViewModel.insertCategory(new Category("Misc.", 100.0, "Expense", "", googleID));
                finish();
                return;
            }
        });

    }
}



