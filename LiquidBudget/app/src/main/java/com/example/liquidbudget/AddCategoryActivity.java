package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);

    Button createCategory = (Button)findViewById(R.id.createCategory);
        createCategory.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(startIntent);
        }
    });

    }
}
