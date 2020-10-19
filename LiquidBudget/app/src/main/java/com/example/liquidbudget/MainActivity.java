package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    ImageView imageView;
    Toolbar toolbar;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        frameLayout = (FrameLayout) findViewById(R.id.frame);

        header = navigationView.getHeaderView(0);
        imageView = (ImageView) header.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        final Context context = this;
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                drawer.closeDrawer(GravityCompat.START);
//                switch (id){
//                    case R.id.nav_budget:
//                        Intent budgetIntent = new Intent(context, SpendingSavingPage.class);
//                        startActivity(budgetIntent);
//                        break;
//                    case R.id.nav_category:
//                        Intent catIntent = new Intent(context, CategoryActivity.class);
//                        startActivity(catIntent);
//                        break;
//                }
//                return false;
//            }
//                int id = menuItem.getItemId();
//                if(id == R.id.nav_budget) {
//                    Intent startIntent = new Intent(context, SpendingSavingPage.class);
//                    startActivity(startIntent);
//                    return true;
//                } else if(id == R.id.nav_category) {
////                    loadFragment(new Fragment());
//                    Intent startIntent = new Intent(context, CategoryActivity.class);
//                    startActivity(startIntent);
//                    return true;
//                } else if(id == R.id.nav_settings) {
//                    loadFragment(new Fragment());
//                    return true;
//                }
//                drawer.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
//
//        Button goToSpending = (Button)findViewById(R.id.goToSpending);
//        goToSpending.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent startIntent = new Intent(getApplicationContext(), SpendingSavingPage.class);
//                startActivity(startIntent);
//            }
//        });
//
//        Button goToCategory = (Button)findViewById(R.id.goToCategory);
//        goToCategory.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
//                startActivity(startIntent);
//            }
//        });
//
//        Button goToSettings = (Button)findViewById(R.id.goToSettings);
//        goToSettings.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent startIntent = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(startIntent);
//            }
//        });

    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_budget:
                Intent budgetIntent = new Intent(MainActivity.this, SpendingSavingPage.class);
                startActivity(budgetIntent);
            case R.id.nav_category:
                Intent catIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(catIntent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}