package com.example.liquidbudget;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_AMOUNT;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_CATEGORY;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_ID;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_NAME;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_DATE;

public class AddIncomeActivity extends AppBaseActivity {

    public static final String EXTRA_INC_NAME = "com.example.liquidbudget.EXTRA_INC_NAME";
    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_AMOUNT = "com.example.liquidbudget.EXTRA_AMOUNT";
    public static final String EXTRA_DATE = "com.example.liquidbudget.EXTRA_DATE";

    public static final String EXTRA_UPDATE_INCOME_ID = "com.example.liquidbudget.EXTRA_UPDATE_ID";

    private EditText editIncName;
    private EditText editCatName;
    private EditText editDoubleAmount;
    private EditText editDate;

    private CategoryViewModel categoryViewModel;
    private String[] categories = {"default"};

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        editIncName = findViewById(R.id.inc_name_edit_text);
        editCatName = findViewById(R.id.cat_name_edit_text);
        editDoubleAmount = findViewById(R.id.amount_edit_text);
        editDate = findViewById(R.id.inc_date_edit_text);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddIncomeActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editDoubleAmount.setFilters(new InputFilter[]{ new DecimalDigitsInputFilter(9, 2)});

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String incomeName = extras.getString(EXTRA_DATA_UPDATE_INCOME_NAME, "");
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_INCOME_CATEGORY, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_INCOME_AMOUNT, 0);
            long dateLong = extras.getLong(EXTRA_DATA_UPDATE_INCOME_DATE);
            if (!incomeName.isEmpty()) {
                editIncName.setText(incomeName);
                editIncName.setSelection(incomeName.length());
                editIncName.requestFocus();
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
            if (dateLong != 0.0) {
                editDate.setText(String.valueOf(dateLong));
                editDate.setSelection(String.valueOf(dateLong).length());
                editDate.requestFocus();
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
        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner_income);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddIncomeActivity.this,
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
                if (TextUtils.isEmpty(editIncName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editCatName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editDoubleAmount.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editDate.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                } else {
                    String incName = editIncName.getText().toString();
                    String catName = editCatName.getText().toString();
                    double amount = Double.parseDouble(editDoubleAmount.getText().toString());
                    long date = Date.parse(editDate.getText().toString());
                    updateIntent.putExtra(EXTRA_INC_NAME, incName);
                    updateIntent.putExtra(EXTRA_CAT_NAME, catName);
                    updateIntent.putExtra(EXTRA_AMOUNT, amount);
                    updateIntent.putExtra(EXTRA_DATE, date);
                    if (extras != null && extras.containsKey(EXTRA_DATA_UPDATE_INCOME_ID)) {
                        int id = extras.getInt(EXTRA_DATA_UPDATE_INCOME_ID, -1);
                        if (id != -1) {
                            updateIntent.putExtra(EXTRA_UPDATE_INCOME_ID, id);
                        }
                    }
                    setResult(RESULT_OK, updateIntent);
                }
                finish();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Income");
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendar.getTime()));

    }
}


    /*private void saveInc() {
        //int IncID = Integer.parseInt(editIncID.getText().toString());
        String name = editIncName.getText().toString();
        String categoryName = editCatName.getText().toString();
        double amount = Double.parseDouble(editDoubleAmount.getText().toString());

        //if(IncID==0 || name.trim().isEmpty() || categoryName.trim().isEmpty() || amount==0.0) {
        if(name.trim().isEmpty() || categoryName.trim().isEmpty() || amount==0.0) {
            Toast.makeText(this, "PLease insert an Income Id, name, category name, and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent d = new Intent();
        //d.putExtra(EXTRA_INC_ID, IncID);
        d.putExtra(EXTRA_INC_NAME, name);
        d.putExtra(EXTRA_CAT_NAME, categoryName);
        d.putExtra(EXTRA_AMOUNT, amount);

        setResult(RESULT_OK, d);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_income_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inc:
                saveInc();
                return true;
            case R.id.button_save:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
 */
