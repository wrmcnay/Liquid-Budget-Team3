package com.example.liquidbudget;

import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SpendingSavingPage extends AppCompatActivity {

    AnyChartView anyChartView;

    // Creating Two Arrays with the data for the Pie Chart
    String[] categories = {"Food/Drink", "Utilities", "Miscellaneous", "Groceries", "Gas", "Saved"};
    int[] spending = {75, 125, 45, 80, 50, 230};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_saving_page);

        anyChartView = findViewById(R.id.any_chart_view);

        // Setting Up Pie Chart
        setupPieChart();
    }

    public void setupPieChart() {
        // Creating Pie Chart
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        // Inputted the data from the two arrays into a list
        for(int i=0; i<categories.length; i++) {
            dataEntries.add(new ValueDataEntry(categories[i], spending[i]));
        }

        // Inserting that data into the Pie Chart
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }


}