package com.example.liquidbudget;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.liquidbudget.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpendingSavingPage extends AppCompatActivity {

    AnyChartView anyChartView;

    String[] categories = {"Food/Drink", "Utilities", "Miscellaneous", "Groceries", "Gas", "Saved"};
    int[] spending = {75, 125, 45, 80, 50, 230};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_saving_page);

        anyChartView = findViewById(R.id.any_chart_view);

        setupPieChart();
    }

    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i=0; i<categories.length; i++) {
            dataEntries.add(new ValueDataEntry(categories[i], spending[i]));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }


}