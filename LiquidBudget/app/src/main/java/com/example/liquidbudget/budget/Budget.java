package com.example.liquidbudget.budget;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.util.ArrayList;

public class Budget extends AppBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_main);



    //private ArrayList<BudgetCategory> mCatList;
    //private CategoryAdapter mAdapter;

   /* @Override
        initList();
        Spinner spinnerCategories = findViewById(R.id.spinner_category);
        mAdapter = new CategoryAdapter(this, mCatList);
        spinnerCategories.setAdapter(mAdapter);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BudgetCategory clickedItem = (BudgetCategory) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCatName();
                Toast.makeText(Budget.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initList() {
        mCatList = new ArrayList<>();
        mCatList.add(new BudgetCategory("Food", R.drawable.food_category));
        /*mCountryList.add(new CountryItem("China", R.drawable.china));
        mCountryList.add(new CountryItem("USA", R.drawable.usa));
        mCountryList.add(new CountryItem("Germany", R.drawable.germany));*/
    }
}
