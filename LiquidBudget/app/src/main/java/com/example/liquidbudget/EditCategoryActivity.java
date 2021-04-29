package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditCategoryActivity extends AppCompatActivity {

    private EditText editCategoryName;
    private EditText editCategoryAmount;

    private String name;
    private Double amount;
    private String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit);

        String categoryName = getIntent().getStringExtra("CategoryName");
        Double categoryAmount = getIntent().getDoubleExtra("CategoryAmount", 0.0);

        editCategoryName = findViewById(R.id.edit_categoryName);
        editCategoryAmount = findViewById(R.id.edit_categoryAmount);

        editCategoryName.setText(categoryName);
        editCategoryAmount.setText(String.valueOf(categoryAmount));


        Button editCategory = (Button)findViewById(R.id.editCategoryFinish);
        editCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                name = editCategoryName.getText().toString().trim();
                amount = Double.parseDouble(editCategoryAmount.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("Name", name);
                intent.putExtra("Amount", amount);
                setResult(RESULT_OK, intent);
                finish();
                return;
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Category");
    }

}
