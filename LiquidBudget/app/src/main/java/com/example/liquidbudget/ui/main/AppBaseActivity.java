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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.liquidbudget.CategoryActivity;
import com.example.liquidbudget.ExpenseDisplayActivity;
import com.example.liquidbudget.IncomeDisplayActivity;
import com.example.liquidbudget.MainActivity;
import com.example.liquidbudget.R;
import com.example.liquidbudget.SpendingSavingPage;
import com.example.liquidbudget.SwipingGraphsTesting.GraphActivity;
import com.example.liquidbudget.budget.Budget;
import com.example.liquidbudget.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout);// The base layout that contains your navigation drawer.
        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();
        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
        // and so on...
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                Intent homeIntent = new Intent(this, SpendingSavingPage.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.nav_budget:
                //Intent budgetIntent = new Intent(this, SpendingSavingPage.class);
                Intent budgetIntent = new Intent(this, Budget.class);
                startActivity(budgetIntent);
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
            case R.id.nav_graphs:
                Intent graphsIntent = new Intent(this, GraphActivity.class);
                startActivity(graphsIntent);
                if(!(this instanceof MainActivity))
                    finish();
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }
}