package com.example.liquidbudget.GraphicalAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.liquidbudget.R;
import com.example.liquidbudget.TutorialDialogue;
import com.example.liquidbudget.ViewCategoryActivity;
import com.example.liquidbudget.budget.Budget;
import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GraphActivity extends AppBaseActivity implements TutorialDialogue.TutorialDialogListener {
    TutorialDialogue d;
    private TextView monthDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private IncomeViewModel incomeViewModel; // to be able to pull from income
    private ExpenseViewModel expenseViewModel; //to be able to pull from expense
    private PieChart chart;
    private BarChart barChart;


    private CategoryViewModel categoryViewModel;
    private final static int MY_REQUEST_CODE= 1;
    private String googleID;
    private Integer numCategories = 0;
    private BottomNavigationView bottomNavigationView;

    //private Calendar calendar;
    private SimpleDateFormat monthFormat;
    private String month;
    private SimpleDateFormat monthTitleFormat;
    private String monthTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_graphs_analysis);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            setContentView(R.layout.activity_graphs_analysis);
            String userName = account.getDisplayName();
            TextView budgetanalysis = findViewById(R.id.usersanalysis);
            budgetanalysis.setText(getString(R.string.users_analysis, userName));

            ViewPager pager = findViewById(R.id.pager);
            pager.setOffscreenPageLimit(4);

            PageAdapter a = new PageAdapter(getSupportFragmentManager());
            pager.setAdapter(a);

            Button button = findViewById(R.id.graphcurrentstandingbutton);
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PieChartCurrentStanding.class);
                    startActivity(intent);
                    return true;
                }
            });
        }
        else {
            setContentView(R.layout.activity_graphs_not_signed_in);
        }

        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        try {
            if(userAccountViewModel.getTutorialState(account.getId()) == 3) {
                Log.d("hello", "yes");
                launchTutorialDialog();
            } else {
                Log.d("hello", "no");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.lstCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        com.example.liquidbudget.ui.DataAdapters.CategoryAdapter adapter = new com.example.liquidbudget.ui.DataAdapters.CategoryAdapter();
        recyclerView.setAdapter(adapter);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();

        //Database
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if(googleAccount != null) {
            try {
                categoryViewModel.getAllCategoriesByGoogleId(googleID).observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categoryList) {
                        adapter.setCategories(categoryList);
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
                        Category myCategory = adapter.getCategoryAtPosition(position);
                        Toast.makeText(GraphActivity.this, "Deleting " +
                                myCategory.getCategoryName(), Toast.LENGTH_LONG).show();

                        // Delete the income
                        categoryViewModel.deleteCategory(myCategory);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CategoryAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Category category = adapter.getCategoryAtPosition(position);
//                launchUpdateCategoryActivity(category);

                Intent viewActivity = new Intent(GraphActivity.this, ViewCategoryActivity.class);
                String catName = category.getCategoryName();
                String catType = category.getCategoryType();
                Double catAmount = category.getCategoryAmount();

                viewActivity.putExtra("CategoryName", catName);
                viewActivity.putExtra("CategoryType", catType);
                viewActivity.putExtra("CategoryAmount", catAmount);
                viewActivity.putExtra("googleid", googleID);
                startActivity(viewActivity);
            }
        });

        try{
            numCategories = categoryViewModel.getNumCategories(googleID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Budget");
    }



    private class PageAdapter extends FragmentPagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch (pos) {
                case 0:
                   f = PieChartBudget.newInstance();
                    break;
                case 1:
                    f = PieChartCategories.newInstance();
                    break;
                case 2:
                    f = PieChartExpenses.newInstance();
                    break;
                case 3:
                    f = PieChartIncomes.newInstance();
                    break;
                case 4:
                    f = BarChartHistory.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public void launchTutorialDialog() {
        d = new TutorialDialogue();
        d.setCurrentLayout(R.layout.tut6);
        d.setPositiveButtonText("OK");
        d.setNegativeButtonText("QUIT");
        d.show(getSupportFragmentManager(), "TutorialDialogFragment");
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        userAccountViewModel.setTutorialState(googleAccount.getId(), 4);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        try {
            if(userAccountViewModel.getTutorialState(account.getId()) == 4){
                d.dismiss();
                d.setCurrentLayout(R.layout.tut7);
                d.setPositiveButtonText("OK");
                d.setNegativeButtonText("QUIT");
                d.show(getSupportFragmentManager(), "TutorialDialogFragment");
                userAccountViewModel.setTutorialState(account.getId(), 5);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}

