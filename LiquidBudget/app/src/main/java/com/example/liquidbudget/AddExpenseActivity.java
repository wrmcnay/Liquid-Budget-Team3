package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.liquidbudget.data.database.CategoryDatabase;
import com.example.liquidbudget.data.datasource.CategoryDataSource;
import com.example.liquidbudget.data.repositories.CategoryRepository;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.util.List;

public class AddExpenseActivity extends AppBaseActivity {


    public static final String EXTRA_EXP_ID = "com.example.liquidbudget.EXTRA_EXP_ID";
    public static final String EXTRA_EXP_NAME = "com.example.liquidbudget.EXTRA_EXP_NAME";
    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_AMOUNT = "com.example.liquidbudget.EXTRA_AMOUNT";

    private EditText editExpID;
    private EditText editExpName;
    private EditText editCatName;
    private EditText editDoubleAmount;

    private CategoryRepository categoryRepository;
    private Spinner category_spinner;

    private Iterable<List<String>> categoryIterable;
    private String[] categories = {"Test1","Test2"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_income_add);

        editExpID = findViewById(R.id.expID);
        editExpName = findViewById(R.id.expName);
        editCatName = findViewById(R.id.category);
        editDoubleAmount = findViewById(R.id.doubleAmount);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Expense");

        CategoryDatabase categoryDatabase = CategoryDatabase.getInstance(this);
        categoryRepository = CategoryRepository.getInstance(CategoryDataSource.getInstance(categoryDatabase.categoryDAO()));

        categoryIterable = categoryRepository.getAllCategoryNames().blockingIterable();


        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this,
                android.R.layout.simple_spinner_item, categories);

    }

    private void saveExp() {
        int ExpID = Integer.parseInt(editExpID.getText().toString());
        String name = editExpName.getText().toString();
        String categoryName = editCatName.getText().toString();
        double amount = Double.parseDouble(editDoubleAmount.getText().toString());

        if(ExpID==0 || name.trim().isEmpty() || categoryName.trim().isEmpty() || amount==0.0) {
            Toast.makeText(this, "PLease insert an Exp Id, name, category name, and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent d = new Intent();
        d.putExtra(EXTRA_EXP_ID, ExpID);
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


}