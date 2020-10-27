package com.example.liquidbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddIncomeExpenseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_income_add);

        Button createIncExp = (Button)findViewById(R.id.buttonSubmit);
        createIncExp.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            setContentView(R.layout.activity_main);
            finish();
            return;
        }
    });

    }
}