package com.example.liquidbudget.GraphicalAnalysis;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liquidbudget.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
public class TestBar extends Fragment {
    BarChart bar;

    public static Fragment newInstance() {
        return new TestBar();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public TestBar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_bar_chart_history, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ProductSans.ttf");

        bar = (BarChart)view.findViewById(R.id.barGraph);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 100f,"Total"));
        entries.add(new BarEntry(1f, 82f,"Obtained"));
        entries.add(new BarEntry(2f, 95f,"Highest"));
        entries.add(new BarEntry(3f, 69f,"Average"));

        BarDataSet bSet = new BarDataSet(entries, "Marks");
        bSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        ArrayList<String> barFactors = new ArrayList<>();
        barFactors.add("Total");
        barFactors.add("Obtained");
        barFactors.add("Highest");
        barFactors.add("Average");


        XAxis xAxis = bar.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarData data = new BarData(bSet);
        data.setBarWidth(0.9f); // set custom bar width
        data.setValueTextSize(12);
        Description description = new Description();
        description.setTextColor(R.color.colorPrimary);
        description.setText("All values in marks");
        bar.setDescription(description);
        bar.setData(data);
        bar.setFitBars(true); // make the x-axis fit exactly all bars
        bar.invalidate(); // refresh
        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));

        Legend l = bar.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTypeface(font);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        ArrayList<LegendEntry> lentries = new ArrayList<>();
        for (int i = 0; i < barFactors.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
            entry.label = barFactors.get(i);
            lentries.add(entry);
        }
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
        l.setCustom(lentries);

        return inflater.inflate(R.layout.fragment_bar_chart_history, container, false);
    }

}