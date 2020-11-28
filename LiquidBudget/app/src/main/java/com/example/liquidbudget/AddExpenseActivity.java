package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_AMOUNT;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_CATEGORY;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_ID;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_NAME;

public class AddExpenseActivity extends AppBaseActivity {

    public static final String EXTRA_EXP_NAME = "com.example.liquidbudget.EXTRA_EXP_NAME";
    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_AMOUNT = "com.example.liquidbudget.EXTRA_AMOUNT";

    public static final String EXTRA_UPDATE_EXPENSE_ID = "com.example.liquidbudget.EXTRA_UPDATE_ID";

    private EditText editExpName;
    private EditText editCatName;
    private EditText editDoubleAmount;

    private CategoryViewModel categoryViewModel;
    private String[] categories = {"default"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_income_add);

        editExpName = findViewById(R.id.expName);
        editCatName = findViewById(R.id.category);
        editDoubleAmount = findViewById(R.id.doubleAmount);

        editDoubleAmount.setFilters(new InputFilter[]{ new DecimalDigitsInputFilter(9, 2)});

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String incomeName = extras.getString(EXTRA_DATA_UPDATE_EXPENSE_NAME, "");
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_EXPENSE_CATEGORY, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_EXPENSE_AMOUNT, 0);
            if (!incomeName.isEmpty()) {
                editExpName.setText(incomeName);
                editExpName.setSelection(incomeName.length());
                editExpName.requestFocus();
            }
            if (!categoryName.isEmpty()) {
                editCatName.setText(categoryName);
                editCatName.setSelection(categoryName.length());
                editCatName.requestFocus();
            }
            if (amount != 0.0) {
                editDoubleAmount.setText(String.valueOf(amount));
                editDoubleAmount.setSelection(String.valueOf(amount).length());
                editDoubleAmount.requestFocus();
            }
        }

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        try {
            List<String> catList = categoryViewModel.getAllCategoryNames();
            catList.add(0, "");
            if (catList.size() > 0) {
                categories = new String[catList.size()];
                categories = catList.toArray(categories);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner_expense);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(adapter.getPosition(editCatName.getText().toString()));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editCatName.setText(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent updateIntent = new Intent();
                if (TextUtils.isEmpty(editExpName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editCatName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editDoubleAmount.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                } else {
                    String incName = editExpName.getText().toString();
                    String catName = editCatName.getText().toString();
                    double amount = Double.parseDouble(editDoubleAmount.getText().toString());
                    updateIntent.putExtra(EXTRA_EXP_NAME, incName);
                    updateIntent.putExtra(EXTRA_CAT_NAME, catName);
                    updateIntent.putExtra(EXTRA_AMOUNT, amount);
                    if (extras != null && extras.containsKey(EXTRA_DATA_UPDATE_EXPENSE_ID)) {
                        int id = extras.getInt(EXTRA_DATA_UPDATE_EXPENSE_ID, -1);
                        if (id != -1) {
                            updateIntent.putExtra(EXTRA_UPDATE_EXPENSE_ID, id);
                        }
                    }
                    setResult(RESULT_OK, updateIntent);
                }
                finish();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Expense");
    }
}

    /*private void saveExp() {
        String name = editExpName.getText().toString();
        String categoryName = editCatName.getText().toString();
        double amount = Double.parseDouble(editDoubleAmount.getText().toString());

        if(name.trim().isEmpty() || categoryName.trim().isEmpty() || amount==0.0) {
            Toast.makeText(this, "PLease insert an Exp Id, name, category name, and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent d = new Intent();
        d.putExtra(EXTRA_EXP_NAME, name);
        d.putExtra(EXTRA_CAT_NAME, categoryName);
        d.putExtra(EXTRA_AMOUNT, amount);

        setResult(RESULT_OK, d);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_exp:
                saveExp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}*/