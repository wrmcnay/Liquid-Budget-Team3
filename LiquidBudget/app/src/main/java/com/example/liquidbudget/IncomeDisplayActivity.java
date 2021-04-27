package com.example.liquidbudget;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.IncomeAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeDisplayActivity extends AppBaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    private static final int UPDATE_INCOME_ACTIVITY_REQUEST_CODE = 2;
    private IncomeViewModel incomeViewModel;
    private String googleID;
    private BottomNavigationView bottomNavigationView;

    public static final String EXTRA_DATA_UPDATE_INCOME_NAME = "extra_income_name_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_CATEGORY = "extra_income_category_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_AMOUNT = "extra_income_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_DATE = "extra_income_date_to_update";
    public static final String EXTRA_DATA_UPDATE_INCOME_ID = "extra_data_inc_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_display);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(3);
        item.setChecked(true);


        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();

        Button incomesButton = (Button) findViewById(R.id.incomes_button);
        Button expensesButton = (Button) findViewById(R.id.expenses_button);

        //incomesButton.setVisibility(View.VISIBLE);
        //incomesButton.setBackgroundColor(Color.TRANSPARENT);
        //expensesButton.setVisibility(View.VISIBLE);
        //expensesButton.setBackgroundColor(Color.TRANSPARENT);

        incomesButton.setClickable(false);

        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expensesPage = new Intent(IncomeDisplayActivity.this, ExpenseDisplayActivity.class);
                startActivity(expensesPage);
                overridePendingTransition(0,0);
            }
        });

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

        //Reverse display order
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        if(googleAccount != null) {
            try {
                incomeViewModel.getAllIncomesByGoogleId(googleID).observe(this, new Observer<List<Income>>() {
                    @Override
                    public void onChanged(List<Income> incomesList) {
                        adapter.setIncomes(incomesList);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
        setTitle("Transactions");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE && data != null) {
            String incomeName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_NAME);
            String categoryName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_CAT_NAME);
            double amount = data.getDoubleExtra(AddIncomeActivity.EXTRA_INC_AMOUNT, 0);
            String date = data.getStringExtra(AddIncomeActivity.EXTRA_INC_DATE);

            Income income = new Income(incomeName, categoryName, amount, date, googleID);
            incomeViewModel.insert(income);
            Toast.makeText(this, "Income Added!", Toast.LENGTH_SHORT).show();
           }
        else if(resultCode == RESULT_OK && requestCode == UPDATE_INCOME_ACTIVITY_REQUEST_CODE && data != null) {
            String updateName = data.getStringExtra(AddIncomeActivity.EXTRA_INC_NAME);
            String updateCategory = data.getStringExtra(AddIncomeActivity.EXTRA_INC_CAT_NAME);
            double updateAmount = data.getDoubleExtra(AddIncomeActivity.EXTRA_INC_AMOUNT, 0);
            String updateDate = data.getStringExtra(AddIncomeActivity.EXTRA_INC_DATE);

            int id = data.getIntExtra(AddIncomeActivity.EXTRA_UPDATE_INCOME_ID, -1);

            if(id != -1) {
                incomeViewModel.updateIncome(new Income(id, updateName, updateCategory, updateAmount, updateDate, googleID));
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
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_DATE, income.getDate());
        intent.putExtra(EXTRA_DATA_UPDATE_INCOME_ID, income.getIncomeID());
        intent.putExtra("googleid", income.getGoogleID());
        startActivityForResult(intent, UPDATE_INCOME_ACTIVITY_REQUEST_CODE);
    }
}

