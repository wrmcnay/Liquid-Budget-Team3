package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpenseDisplayActivity extends AppBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);


        FloatingActionButton toAdd = (FloatingActionButton) findViewById(R.id.addExpenseButton);
        toAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent addExpIntent = new Intent(ExpenseDisplayActivity.this, AddExpenseActivity.class);
                startActivity(addExpIntent);
                return;
            }
        });

    }
}
