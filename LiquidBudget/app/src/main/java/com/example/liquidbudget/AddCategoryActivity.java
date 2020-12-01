package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.liquidbudget.ViewCategoryActivity.EXTRA_DATA_UPDATE_CATEGORY_AMOUNT;
import static com.example.liquidbudget.ViewCategoryActivity.EXTRA_DATA_UPDATE_CATEGORY_NAME;
import static com.example.liquidbudget.ViewCategoryActivity.EXTRA_DATA_UPDATE_CATEGORY_TYPE;
import static com.example.liquidbudget.ViewCategoryActivity.EXTRA_DATA_UPDATE_CATEGORY_ID;

public class AddCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_CAT_TYPE = "com.example.liquidbudget.EXTRA_CAT_TYPE";
    public static final String EXTRA_CAT_AMOUNT = "com.example.liquidbudget.EXTRA_CAT_AMOUNT";

    public static final String EXTRA_UPDATE_CATEGORY_ID = "com.example.liquidbudget.EXTRA_UPDATE_CAT_ID";

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

        final Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_CATEGORY_NAME, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_CATEGORY_AMOUNT, 0);
            String categoryType = extras.getString(EXTRA_DATA_UPDATE_CATEGORY_TYPE, "");
            if (!categoryName.isEmpty()) {
                editCategoryName.setText(categoryName);
                editCategoryName.setSelection(categoryName.length());
                editCategoryName.requestFocus();
            }
            if (amount != 0.0) {
                editCategoryAmount.setText(String.valueOf(amount));
                editCategoryAmount.setSelection(String.valueOf(amount).length());
                editCategoryAmount.requestFocus();
            }
            if (!categoryType.isEmpty()) {
                this.categoryType = categoryType;
                if(categoryType.equals("Income"))
                    editCategoryType.check(R.id.incomeRadio);
                else
                    editCategoryType.check(R.id.expenseRadio);
                editCategoryType.requestFocus();
            }
        }

        createCategory.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            name = editCategoryName.getText().toString().trim();
            amount = Double.parseDouble(editCategoryAmount.getText().toString());
            type = categoryType;
            Intent intent = new Intent();
            //intent.putExtra("Name", name);
            //intent.putExtra("Amount", amount);
            //intent.putExtra("Type", type);
            intent.putExtra(EXTRA_CAT_NAME, name);
            intent.putExtra(EXTRA_CAT_AMOUNT, amount);
            intent.putExtra(EXTRA_CAT_TYPE, type);
            if(extras != null && extras.containsKey(EXTRA_DATA_UPDATE_CATEGORY_ID)) {
                int id = extras.getInt(EXTRA_DATA_UPDATE_CATEGORY_ID, -1);
                if(id != -1) {
                    intent.putExtra(EXTRA_UPDATE_CATEGORY_ID, id);
                }
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Category");
    }

    public void addListenerOnButton(){

    }
}
