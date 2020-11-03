package com.example.liquidbudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.liquidbudget.data.repositories.IncomeRepository;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.IncomeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class IncomeDisplayActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1;
    private IncomeViewModel incomeViewModel;
    private IncomeRepository incomeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_display);

        FloatingActionButton addIncomeBtn = findViewById(R.id.add_income_button);
        addIncomeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeDisplayActivity.this, AddIncomeActivity.class);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.Income_Recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        IncomeAdapter adapter = new IncomeAdapter();
        recyclerView.setAdapter(adapter);

        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        incomeViewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomesList) {
                adapter.setIncomes(incomesList);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE) {
            int incID = data.getIntExtra(AddIncomeActivity.EXTRA_INC_ID, 1);
            String incomeName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_NAME);
            String categoryName = data.getStringExtra(AddIncomeActivity.EXTRA_CAT_NAME);
            double amount = data.getDoubleExtra(AddIncomeActivity.EXTRA_AMOUNT, 0);

            Income income = new Income(incomeName, categoryName, amount);
            income.setIncomeID(incID);
            incomeViewModel.insert(income);
            Toast.makeText(this, "Income Added!", Toast.LENGTH_SHORT).show();
           }
        else {
            Toast.makeText(this, "Income not saved", Toast.LENGTH_SHORT).show();
        }
    }
}

