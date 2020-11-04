package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liquidbudget.ui.main.AppBaseActivity;

public class ExpenseDisplayActivity extends AppBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_display);

        Button createIncExp = (Button)findViewById(R.id.buttonHome);
        createIncExp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mainIntent = new Intent(ExpenseDisplayActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                return;
            }
        });


        Button toAdd = (Button)findViewById(R.id.buttonAdd);
        toAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent addExpIntent = new Intent(ExpenseDisplayActivity.this, AddIncomeExpenseActivity.class);
                startActivity(addExpIntent);
                return;
            }
        });

    }
}
