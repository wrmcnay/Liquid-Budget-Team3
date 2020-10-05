package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewCategoryActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(CategoryActivity.CATEGORY_NAME);
        TextView displayName = (TextView) findViewById(R.id.category_display);

        displayName.setText(categoryName);
    }
}
