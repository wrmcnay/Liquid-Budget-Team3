package com.example.liquidbudget.GraphicalAnalysis;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class PieChartBudget extends SimpleFragment implements OnChartValueSelectedListener {

    private ExpenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private PieChart chart;
    ArrayList<PieEntry> entries;
    PieDataSet dataSet;
    ArrayList<Integer> colors;
    PieData pieData;
    GoogleSignInAccount account;

    private Calendar calendar;
    private SimpleDateFormat monthFormat;
    private String month;
    private SimpleDateFormat monthTitleFormat;
    private String monthTitle;

    @NonNull
    public static Fragment newInstance() {
        return new PieChartBudget();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart_budget, container, false);

        chart = v.findViewById(R.id.pieChart1);
        chart.getDescription().setEnabled(false);

        account = GoogleSignIn.getLastSignedInAccount(getContext());

        formatChart();
        try {
            populateData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chart.setOnChartValueSelectedListener(this);

        chart.invalidate();

        return v;
    }

    private SpannableString generateCenterSpannableText() {
        calendar = Calendar.getInstance();
        monthTitleFormat = new SimpleDateFormat("MMMM");
        monthTitle = monthTitleFormat.format(calendar.getTime());
        SpannableString s = new SpannableString(monthTitle.toString().toUpperCase());//middle of chart
        s.setSpan(new RelativeSizeSpan(1.75f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    private void populateData() throws ExecutionException, InterruptedException {
        entries = new ArrayList<>();

        calendar = Calendar.getInstance();
        monthFormat = new SimpleDateFormat("MM");
        month = monthFormat.format(calendar.getTime());

        if(account != null) {
            expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
            double totalExpense = expenseViewModel.getMonthSumTotalForGoogleID(account.getId(), month);
            entries.add(new PieEntry((float) totalExpense, "Expenses"));

            //remaining to spend
            incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
            calendar = Calendar.getInstance();
            monthFormat = new SimpleDateFormat("MM");
            month = monthFormat.format(calendar.getTime());
            double leftToSpend = incomeViewModel.getMonthSumTotalForGoogleID(account.getId(), month) - totalExpense;
            entries.add(new PieEntry((float) leftToSpend, "Remaining Income"));


            dataSet = new PieDataSet(entries, "");
            dataSet.setDrawIcons(true);

            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);

            setColors();
            dataSet.setColors(colors);

            pieData = new PieData(dataSet);
            pieData.setValueFormatter(new DefaultAxisValueFormatter(2));
            pieData.setValueTextSize(15f);
            pieData.setDrawValues(true);
            pieData.setValueTextColor(Color.BLACK);

            chart.setData(pieData);

            dataSet.setDrawIcons(true);

            dataSet.setSliceSpace(5f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(10f);

            chart.invalidate();
        }

    }


    private void setColors() {
        colors = new ArrayList<>();

        colors.add(Color.rgb(206, 139, 134));
        colors.add(Color.rgb(165, 225, 173));


    }

    private void formatChart() {
        chart.getDescription().setEnabled(true);
        chart.setCenterText(generateCenterSpannableText());

        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 0, 5, 10);

        chart.setDragDecelerationFrictionCoef(0.1f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(0xE9E0F8);

        chart.setClickable(true);

        chart.setTransparentCircleColor(Color.LTGRAY);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(35f);
        chart.setTransparentCircleRadius(44f);

        chart.setCenterTextColor(Color.GRAY);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1800, Easing.EaseInOutQuad);

        chart.getLegend().setEnabled(false);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(14f);
        chart.setDrawEntryLabels(true);
    }

    @Override
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
}