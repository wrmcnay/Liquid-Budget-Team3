package com.example.liquidbudget.budget;

import com.example.liquidbudget.AddCategoryActivity;
import com.example.liquidbudget.CategoryActivity;
import com.example.liquidbudget.PreloadCategories;
import com.example.liquidbudget.R;
import com.example.liquidbudget.ViewCategoryActivity;
import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Budget extends AppBaseActivity implements OnChartValueSelectedListener {

    private IncomeViewModel incomeViewModel; // to be able to pull from income
    private ExpenseViewModel expenseViewModel; //to be able to pull from expense
    private PieChart chart;
    ArrayList<PieEntry> entries;
    PieDataSet dataSet;
    ArrayList<Integer> colors;
    PieData pieData;

    private CategoryViewModel categoryViewModel;
    private final static int MY_REQUEST_CODE= 1;
    private String googleID;
    private Integer numCategories = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String userName = account.getDisplayName();
            TextView homepagehello = findViewById(R.id.usersbudget);
            homepagehello.setText(getString(R.string.users_budget, userName));
        }

        chart = findViewById(R.id.pie_chart);

        populateData();
        formatChart();

        chart.setOnChartValueSelectedListener(this);
        chart.invalidate();


        //category list

        // init view
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
                        Toast.makeText(Budget.this, "Deleting " +
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

                Intent viewActivity = new Intent(Budget.this, ViewCategoryActivity.class);
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

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Budget");//middle of chart
        s.setSpan(new RelativeSizeSpan(1.75f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    private void populateData() {
        entries = new ArrayList<>();
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        incomeViewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomesList) { // when a new income is added

                try {
                    Double am = incomeViewModel.getSumTotal();
                    String name;
                    //for (Income inc : incomesList) { // each income on list
                    name = "Total Monthly Incomes";
                    if(am != null) {
                        entries.add(new PieEntry((float)(double) am, name)); //create an new entry on the pie chart
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dataSet = new PieDataSet(entries, "");
                dataSet.setDrawIcons(true);

                dataSet.setSliceSpace(3f);
                dataSet.setIconsOffset(new MPPointF(0, 40));
                dataSet.setSelectionShift(5f);

                setColors();
                dataSet.setColors(colors);

                pieData = new PieData(dataSet);
                pieData.setValueFormatter(new DefaultAxisValueFormatter(2));
                pieData.setValueTextSize(11f);
                pieData.setDrawValues(true);
                pieData.setValueTextColor(Color.BLACK);

                chart.setData(pieData);

                dataSet.setDrawIcons(true);

                dataSet.setSliceSpace(5f);
                dataSet.setIconsOffset(new MPPointF(0, 40));
                dataSet.setSelectionShift(5f);

                chart.invalidate();

            }
        });
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenseList) { // when a new income is added

                try {
                    Double am = expenseViewModel.getSumTotal();
                    String name;

                    name = "Total Monthy Expenses";
                    if (am != null) {
                        entries.add(new PieEntry((float)(double) am, name)); //create an new entry on the pie chart
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dataSet = new PieDataSet(entries, "");
                dataSet.setDrawIcons(true);

                dataSet.setSliceSpace(3f);
                dataSet.setIconsOffset(new MPPointF(0, 40));
                dataSet.setSelectionShift(5f);

                setColors();
                dataSet.setColors(colors);

                pieData = new PieData(dataSet);
                pieData.setValueFormatter(new DefaultAxisValueFormatter(2));
                pieData.setValueTextSize(11f);
                pieData.setDrawValues(true);
                pieData.setValueTextColor(Color.BLACK);

                chart.setData(pieData);

                dataSet.setDrawIcons(true);

                dataSet.setSliceSpace(5f);
                dataSet.setIconsOffset(new MPPointF(0, 40));
                dataSet.setSelectionShift(5f);

                chart.invalidate();

            }
        });
    }

    private void setColors() {
        colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

    }

    private void formatChart() {
        chart.getDescription().setEnabled(true);
        chart.setCenterText(generateCenterSpannableText());

        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.LTGRAY);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(35f);
        chart.setTransparentCircleRadius(44f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1800, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setFormSize(20);
        l.setFormToTextSpace(5);
        l.setTextSize(12);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(4f);
        l.setYOffset(0f);
        l.setWordWrapEnabled(true);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(16f);
        chart.setDrawEntryLabels(true);
    }









}