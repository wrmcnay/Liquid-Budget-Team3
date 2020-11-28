package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_AMOUNT;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_CATEGORY;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_ID;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_NAME;

public class AddExpenseActivity extends AppBaseActivity {

    public static final String EXTRA_EXP_NAME = "com.example.liquidbudget.EXTRA_EXP_NAME";
    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_AMOUNT = "com.example.liquidbudget.EXTRA_AMOUNT";

    public static final String EXTRA_UPDATE_ID = "com.example.liquidbudget.EXTRA_UPDATE_ID";

    private EditText editExpName;
    private EditText editCatName;
    private EditText editDoubleAmount;

    private CategoryViewModel categoryViewModel;
    private String[] categories = {"default"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editExpName = findViewById(R.id.exp_name_edit_text);
        editCatName = findViewById(R.id.cat_name_edit_text);
        editDoubleAmount = findViewById(R.id.amount_text_edit);

        editDoubleAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(9, 2)});

        //int id = -1;
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String incomeName = extras.getString(EXTRA_DATA_UPDATE_NAME, "");
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_CATEGORY, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_AMOUNT, 0);
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
            if (catList.size() > 0) {
                categories = new String[catList.size()];
                categories = catList.toArray(categories);
            }
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner_expense);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


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
                    if (extras != null && extras.containsKey(EXTRA_DATA_UPDATE_ID)) {
                        int id = extras.getInt(EXTRA_DATA_UPDATE_ID, -1);
                        if (id != -1) {
                            updateIntent.putExtra(EXTRA_UPDATE_ID, id);
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
    }*/