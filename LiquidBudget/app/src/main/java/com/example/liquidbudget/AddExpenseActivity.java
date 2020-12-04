package com.example.liquidbudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_AMOUNT;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_CATEGORY;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_ID;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_NAME;
import static com.example.liquidbudget.ExpenseDisplayActivity.EXTRA_DATA_UPDATE_EXPENSE_DATE;

public class AddExpenseActivity extends AppBaseActivity {

    public static final String EXTRA_EXP_NAME = "com.example.liquidbudget.EXTRA_EXP_NAME";
    public static final String EXTRA_EXP_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_EXP_AMOUNT = "com.example.liquidbudget.EXTRA_EXP_AMOUNT";
    public static final String EXTRA_EXP_DATE = "com.example.liquidbudget.EXTRA_EXP_DATE";

    public static final String EXTRA_UPDATE_EXPENSE_ID = "com.example.liquidbudget.EXTRA_UPDATE_EXP_ID";

    private EditText editExpName;
    private EditText editExpAmount;
    private EditText editExpDate;
    private Spinner categorySpinner;

    private String googleID;

    private CategoryViewModel categoryViewModel;
    private ArrayList<String> categories;

    final Calendar myExpCalendar = Calendar.getInstance();
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();

        editExpName = findViewById(R.id.exp_name_edit_text);
        editExpAmount = findViewById(R.id.exp_amount_edit_text);
        editExpDate = findViewById(R.id.exp_date_edit_text);
        categorySpinner = findViewById(R.id.category_spinner_expense);

        DatePickerDialog.OnDateSetListener datePickerExp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myExpCalendar.set(Calendar.YEAR, year);
                myExpCalendar.set(Calendar.MONTH, month);
                myExpCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        editExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddExpenseActivity.this, datePickerExp, myExpCalendar.get(Calendar.YEAR), myExpCalendar.get(Calendar.MONTH), myExpCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editExpAmount.setFilters(new InputFilter[]{ new DecimalDigitsInputFilter(9, 2)});


        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if(googleAccount != null) {
            try {
                List<String> catList = categoryViewModel.getAllExpenseCategoryNamesByGoogleId(googleID);
                categories = new ArrayList<>();
                if (catList.size() > 0) {
                    categories = (ArrayList<String>) catList;
                    //Arrays.sort(categories);
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Things can be done here if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tryDataPopulation();

        final Button button = findViewById(R.id.exp_button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent updateIntent = new Intent();
                if (TextUtils.isEmpty(editExpName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(categorySpinner.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editExpAmount.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editExpDate.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }else {
                    String expName = editExpName.getText().toString();
                    String catName = categorySpinner.getSelectedItem().toString();
                    double amount = Double.parseDouble(editExpAmount.getText().toString());
                    String date = editExpDate.getText().toString();

                    updateIntent.putExtra(EXTRA_EXP_NAME, expName);
                    updateIntent.putExtra(EXTRA_EXP_CAT_NAME, catName);
                    updateIntent.putExtra(EXTRA_EXP_AMOUNT, amount);
                    updateIntent.putExtra(EXTRA_EXP_DATE, date);
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

    private void tryDataPopulation(){
        extras = getIntent().getExtras();

        if (extras != null) {
            String expenseName = extras.getString(EXTRA_DATA_UPDATE_EXPENSE_NAME, "");
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_EXPENSE_CATEGORY, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_EXPENSE_AMOUNT, 0);
            String date = extras.getString(EXTRA_DATA_UPDATE_EXPENSE_DATE, "");
            if (!expenseName.isEmpty()) {
                editExpName.setText(expenseName);
                editExpName.setSelection(expenseName.length());
                editExpName.requestFocus();
            }
            if (!categoryName.isEmpty()) {
                categorySpinner.setSelection(categories.indexOf(categoryName));
                categorySpinner.requestFocus();
            }
            if (amount != 0.0) {
                editExpAmount.setText(String.valueOf(amount));
                editExpAmount.setSelection(String.valueOf(amount).length());
                editExpAmount.requestFocus();
            }
            if (!date.isEmpty()) {
                editExpDate.setText(date);
                editExpDate.setSelection(date.length());
                editExpDate.requestFocus();
            }
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExpDate.setText(sdf.format(myExpCalendar.getTime()));

    }
}