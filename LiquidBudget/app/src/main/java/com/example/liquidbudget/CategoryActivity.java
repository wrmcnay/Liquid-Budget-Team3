package com.example.liquidbudget;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity{
    public static final String CATEGORY_NAME = "com.example.liquidbudget.CATEGORY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);

        Button goToAddCategory = (Button)findViewById(R.id.goToAddCategory);
        goToAddCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivity(startIntent);
            }
        });

        /**
         * TODO: How can we dynamically assign buttons their own IDs?
         */
        Button category_test = (Button)findViewById(R.id.category_test);
        category_test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openCategoryView();
            }
        });

    }
    public void openCategoryView() {
        String testCategory = "Test Category";

        Intent startIntent = new Intent(getApplicationContext(), ViewCategoryActivity.class);
        startIntent.putExtra(CATEGORY_NAME, testCategory);
        startActivity(startIntent);
    }

}
