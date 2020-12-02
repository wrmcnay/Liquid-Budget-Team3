package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText editCategoryName;
    private EditText editCategoryAmount;
    private RadioGroup editCategoryType;
    private RadioButton typeButton;

    private String categoryType;
    private String name;
    private Double amount;
    private String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);

        Button createCategory = (Button)findViewById(R.id.createCategory);
        editCategoryName = findViewById(R.id.edit_categoryName);
        editCategoryAmount = findViewById(R.id.edit_categoryAmount);
        editCategoryType = findViewById(R.id.edit_CategoryType);

        editCategoryType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.incomeRadio:
                        categoryType = "Income";
                        break;
                    case R.id.expenseRadio:
                        categoryType = "Expense";
                        break;
                    default:
                        categoryType = "None";
                        break;
                }
            }
        });

//        editCategoryType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int type = editCategoryType.getCheckedRadioButtonId();
//                typeButton = (RadioButton) findViewById(type);
//                categoryType = typeButton.getText().toString();
//            }
//        });

        createCategory.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            name = editCategoryName.getText().toString().trim();
            amount = Double.parseDouble(editCategoryAmount.getText().toString());
            type = categoryType;
            Intent intent = new Intent();
            intent.putExtra("Name", name);
            intent.putExtra("Amount", amount);
            intent.putExtra("Type", type);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
    });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Category");
    }

    public void addListenerOnButton(){

    }
}
