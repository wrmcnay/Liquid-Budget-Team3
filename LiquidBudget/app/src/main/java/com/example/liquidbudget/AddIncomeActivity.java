package com.example.liquidbudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_AMOUNT;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_CATEGORY;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_ID;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_NAME;
import static com.example.liquidbudget.IncomeDisplayActivity.EXTRA_DATA_UPDATE_INCOME_DATE;

public class AddIncomeActivity extends AppBaseActivity {

    public static final String EXTRA_INC_NAME = "com.example.liquidbudget.EXTRA_INC_NAME";
    public static final String EXTRA_INC_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_INC_AMOUNT = "com.example.liquidbudget.EXTRA_INC_AMOUNT";
    public static final String EXTRA_INC_DATE = "com.example.liquidbudget.EXTRA_INC_DATE";

    public static final String EXTRA_UPDATE_INCOME_ID = "com.example.liquidbudget.EXTRA_UPDATE_INC_ID";

    private EditText editIncName;
    private EditText editIncAmount;
    private EditText editIncDate;

    private String googleID;

    private CategoryViewModel categoryViewModel;
    private ArrayList<String> categories;

    final Calendar myIncCalendar = Calendar.getInstance();
    Bundle extras;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();



        editIncName = findViewById(R.id.inc_name_edit_text);
        editIncAmount = findViewById(R.id.inc_amount_edit_text);
        editIncDate = findViewById(R.id.inc_date_edit_text);
        categorySpinner = findViewById(R.id.category_spinner_income);

        DatePickerDialog.OnDateSetListener datePickerInc = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myIncCalendar.set(Calendar.YEAR, year);
                myIncCalendar.set(Calendar.MONTH, month);
                myIncCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        editIncDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddIncomeActivity.this, datePickerInc, myIncCalendar.get(Calendar.YEAR), myIncCalendar.get(Calendar.MONTH), myIncCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editIncAmount.setFilters(new InputFilter[]{ new DecimalDigitsInputFilter(9, 2)});

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if(googleAccount != null) {
            try {
                List<String> catList = categoryViewModel.getAllIncomeCategoryNamesByGoogleId(googleID);
                categories = new ArrayList<>();
                if (catList.size() > 0) {
                    categories = (ArrayList<String>) catList;
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddIncomeActivity.this,
                android.R.layout.simple_spinner_item, categories);
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

        final Button button = findViewById(R.id.inc_button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent updateIntent = new Intent();
                if (TextUtils.isEmpty(editIncName.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
               if (TextUtils.isEmpty(categorySpinner.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editIncAmount.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                }
                if (TextUtils.isEmpty(editIncDate.getText())) {
                    setResult(RESULT_CANCELED, updateIntent);
                } else {
                    String incName = editIncName.getText().toString();
                    String catName = categorySpinner.getSelectedItem().toString();
                    double amount = Double.parseDouble(editIncAmount.getText().toString());
                    String date = editIncDate.getText().toString();

                    updateIntent.putExtra(EXTRA_INC_NAME, incName);
                    updateIntent.putExtra(EXTRA_INC_CAT_NAME, catName);
                    updateIntent.putExtra(EXTRA_INC_AMOUNT, amount);
                    updateIntent.putExtra(EXTRA_INC_DATE, date);
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

    private void tryDataPopulation(){
        extras = getIntent().getExtras();
        if (extras != null) {
            String incomeName = extras.getString(EXTRA_DATA_UPDATE_INCOME_NAME, "");
            String categoryName = extras.getString(EXTRA_DATA_UPDATE_INCOME_CATEGORY, "");
            double amount = extras.getDouble(EXTRA_DATA_UPDATE_INCOME_AMOUNT, 0);
            String date = extras.getString(EXTRA_DATA_UPDATE_INCOME_DATE, "");
            if (!incomeName.isEmpty()) {
                editIncName.setText(incomeName);
                editIncName.setSelection(incomeName.length());
                editIncName.requestFocus();
            }
            if (!categoryName.isEmpty()) {
                categorySpinner.setSelection(categories.indexOf(categoryName));
            }
            if (amount != 0.0) {
                editIncAmount.setText(String.valueOf(amount));
                editIncAmount.setSelection(String.valueOf(amount).length());
                editIncAmount.requestFocus();
            }
            if (!date.isEmpty()) {
                editIncDate.setText(date);
                editIncDate.setSelection(date.length());
                editIncDate.requestFocus();
            }
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editIncDate.setText(sdf.format(myIncCalendar.getTime()));

    }
}