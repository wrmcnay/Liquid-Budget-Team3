package com.example.liquidbudget;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.IncomeAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class IncomeDisplayActivity extends AppBaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    private static final int UPDATE_INCOME_ACTIVITY_REQUEST_CODE = 2;
    private IncomeViewModel incomeViewModel;

    public static final String EXTRA_DATA_UPDATE_INCOME_NAME = "extra_income_name_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_CATEGORY = "extra_income_category_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_AMOUNT = "extra_income_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_ID = "extra_data_id";

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

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Income myIncome = adapter.getIncomeAtPosition(position);
                        Toast.makeText(IncomeDisplayActivity.this, "Deleting " +
                                myIncome.getIncomeName(), Toast.LENGTH_LONG).show();

                        // Delete the income
                        incomeViewModel.deleteIncome(myIncome);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new IncomeAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Income income = adapter.getIncomeAtPosition(position);
                launchUpdateIncomeActivity(income);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Incomes");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE && data != null) {
            String incomeName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_NAME);
            String categoryName = data.getStringExtra(AddIncomeActivity.EXTRA_CAT_NAME);
            double amount = data.getDoubleExtra(AddIncomeActivity.EXTRA_AMOUNT, 0);
            Income income = new Income(incomeName, categoryName, amount);
            incomeViewModel.insert(income);
            Toast.makeText(this, "Income Added!", Toast.LENGTH_SHORT).show();
           }
        else if(resultCode == RESULT_OK && requestCode == UPDATE_INCOME_ACTIVITY_REQUEST_CODE && data != null) {
            String updateName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_NAME);
            String updateCategory = data.getStringExtra(AddIncomeActivity.EXTRA_CAT_NAME);
            double updateAmount = data.getDoubleExtra(AddIncomeActivity.EXTRA_AMOUNT, 0);
            int id = data.getIntExtra(AddIncomeActivity.EXTRA_UPDATE_INCOME_ID, -1);

            if(id != -1) {
                incomeViewModel.updateIncome(new Income(id, updateName, updateCategory, updateAmount));
            }
            else {
                Toast.makeText(this, "Income not able to update", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Income not saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchUpdateIncomeActivity(Income income) {
        Intent intent = new Intent(this, AddIncomeActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_NAME, income.getIncomeName());
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_CATEGORY, income.getCategoryName());
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_AMOUNT, income.getAmount());
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_ID, income.getIncomeID());
        startActivityForResult(intent, UPDATE_INCOME_ACTIVITY_REQUEST_CODE);
    }
}

