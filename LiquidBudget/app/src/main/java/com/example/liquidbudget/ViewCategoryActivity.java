package com.example.liquidbudget;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewCategoryActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_view);

        String categoryName = getIntent().getStringExtra("CategoryName");
        TextView displayName = (TextView) findViewById(R.id.category_display);
        displayName.setText(categoryName);

        displayName.setText(categoryName);
    }
}
