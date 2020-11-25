package com.example.liquidbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.liquidbudget.ui.main.AppBaseActivity;


public class AddIncomeActivity extends AppBaseActivity {

    public static final String EXTRA_INC_ID = "com.example.liquidbudget.EXTRA_INC_ID";
    public static final String EXTRA_INC_NAME = "com.example.liquidbudget.EXTRA_INC_NAME";
    public static final String EXTRA_CAT_NAME = "com.example.liquidbudget.EXTRA_CAT_NAME";
    public static final String EXTRA_AMOUNT = "com.example.liquidbudget.EXTRA_AMOUNT";

    private EditText editIncID;
    private EditText editIncName;
    private EditText editCatName;
    private EditText editDoubleAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        //editIncID = findViewById(R.id.inc_id_edit_text);
        editIncName = findViewById(R.id.inc_name_edit_text);
        editCatName = findViewById(R.id.cat_name_edit_text);
        editDoubleAmount = findViewById(R.id.amount_text_edit);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Income");
    }

    private void saveInc() {
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}