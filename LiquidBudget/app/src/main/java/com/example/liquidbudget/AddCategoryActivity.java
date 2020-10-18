package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);

        Button createCategory = (Button)findViewById(R.id.createCategory);
        final EditText editCategoryName = findViewById(R.id.edit_categoryName);

        createCategory.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            String name = editCategoryName.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("Name", name);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
    });

    }
}
