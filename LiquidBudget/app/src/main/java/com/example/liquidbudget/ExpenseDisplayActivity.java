package com.example.liquidbudget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liquidbudget.ui.main.AppBaseActivity;

public class ExpenseDisplayActivity extends AppBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_income_add);

        Button createIncExp = (Button)findViewById(R.id.buttonHome);
        createIncExp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setContentView(R.layout.activity_main);
                finish();
                return;
            }
        });


        Button toAdd = (Button)findViewById(R.id.buttonAdd);
        toAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setContentView(R.layout.expense_income_add);
                finish();
                return;
            }
        });

    }
}
