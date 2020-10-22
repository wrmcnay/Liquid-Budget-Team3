package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.liquidbudget.data.JacobDBWork.UserDisplayActivity;
import com.example.liquidbudget.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
//                Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(homeIntent);
                break;
            case R.id.nav_budget:
                Intent budgetIntent = new Intent(MainActivity.this, SpendingSavingPage.class);
                startActivity(budgetIntent);
                break;
            case R.id.nav_category:
                Intent catIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(catIntent);
                break;
            case R.id.nav_settings:
                Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.nav_users:
                Intent usersIntent = new Intent(MainActivity.this, UserDisplayActivity.class);
                startActivity(usersIntent);
                break;
            case R.id.nav_incExp:
                Intent addIncExpIntent = new Intent(MainActivity.this, AddIncomeExpenseActivity.class);
                startActivity(addIncExpIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
