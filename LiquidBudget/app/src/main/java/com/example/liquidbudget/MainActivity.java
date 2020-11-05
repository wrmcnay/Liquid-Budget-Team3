package com.example.liquidbudget;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.liquidbudget.ui.main.AppBaseActivity;

public class MainActivity extends AppBaseActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_test);
    }
}
