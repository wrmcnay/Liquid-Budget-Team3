package com.example.liquidbudget.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.liquidbudget.CategoryActivity;
import com.example.liquidbudget.ExpenseDisplayActivity;
import com.example.liquidbudget.GraphicalAnalysis.GraphActivity;
import com.example.liquidbudget.IncomeDisplayActivity;
import com.example.liquidbudget.MainActivity;
import com.example.liquidbudget.R;
import com.example.liquidbudget.budget.Budget;
import com.example.liquidbudget.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class AppNavigationActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub2; //This is the framelayout to keep your content view
    private BottomNavigationView bottomNavigationView;
    private Menu bottommenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout_bottom);
        view_stub2 = (FrameLayout) findViewById(R.id.view_stub2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottommenu = bottomNavigationView.getMenu();
        for(int i = 0; i < bottommenu.size(); i++) {
            bottommenu.getItem(i).setOnMenuItemClickListener(this);
        }
        // and so on...
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub2
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub2 != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub2, false);
            view_stub2.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub2 != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub2.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub2 != null) {
            view_stub2.addView(view, params);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
//                Intent homeIntent = new Intent(this, SpendingSavingPage.class);
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.nav_budget:
                Intent budgetIntent = new Intent(this, Budget.class);
                startActivity(budgetIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
            case R.id.nav_analysis:
                Intent analysisIntent = new Intent(this, GraphActivity.class);
                startActivity(analysisIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
            case R.id.nav_category:
                Intent catIntent = new Intent(this, CategoryActivity.class);
                startActivity(catIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
            case R.id.nav_settings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
            case R.id.nav_incExp:
                Intent addIncExpIntent = new Intent(this, ExpenseDisplayActivity.class);
                startActivity(addIncExpIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
            case R.id.nav_incomes:
                Intent incomesIntent = new Intent(this, IncomeDisplayActivity.class);
                startActivity(incomesIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
        }
        return false;
    }
}