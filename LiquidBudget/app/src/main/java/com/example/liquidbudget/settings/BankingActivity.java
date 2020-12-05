package com.example.liquidbudget.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;

public class BankingActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Banking");
        setContentView(R.layout.activity_banking);
    }
}