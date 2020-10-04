package com.example.liquidbudget;

import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SpendingSavingPage extends AppCompatActivity {

    AnyChartView anyChartView;

    // Creating Two Arrays with the data for the Pie Chart
    String[] categories = {"Food/Drink", "Utilities", "Miscellaneous", "Saved"};
    int[] spending = {75, 125, 80, 230};

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

        // Formatting the Chart
        pie.title("Spending Categories for July 2020");
        pie.labels().position("outside");

        pie.legend().position("center-bottom");
        pie.legend().itemsLayout(LegendLayout.HORIZONTAL);
        pie.legend().align(Align.CENTER);

        pie.credits().enabled(false);
        anyChartView.setChart(pie);
    }

}
