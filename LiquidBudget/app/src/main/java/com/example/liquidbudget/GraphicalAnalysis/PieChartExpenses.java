package com.example.liquidbudget.GraphicalAnalysis;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liquidbudget.R;
import com.example.liquidbudget.data.entities.Expense;
import com.example.liquidbudget.data.viewmodels.ExpenseViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PieChartExpenses extends SimpleFragment {

    private Context context;

    private ExpenseViewModel expenseViewModel;
    private PieChart chart;
    ArrayList<PieEntry> entries;
    PieDataSet dataSet;
    ArrayList<Integer> colors;
    PieData pieData;
    GoogleSignInAccount account;

    @NonNull
    public static Fragment newInstance() {
        return new PieChartExpenses();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart_expenses, container, false);

        account = GoogleSignIn.getLastSignedInAccount(getContext());

        chart = v.findViewById(R.id.pieChart1);
        chart.getDescription().setEnabled(false);

        formatChart();
        try {
            populateData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return v;
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("EXPENSES");
        s.setSpan(new RelativeSizeSpan(1.75f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    private void populateData() throws ExecutionException, InterruptedException {
        entries = new ArrayList<>();
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        if(account != null) {
            expenseViewModel.getAllExpensesByGoogleID(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expenseList) {
                    entries.clear();
                    double am;
                    String name;
                    for (Expense exp : expenseList) {
                        am = exp.getAmount();
                        name = exp.getExpenseName();
                        entries.add(new PieEntry((float) am, name));
                    }

                    dataSet = new PieDataSet(entries, "");
                    dataSet.setDrawIcons(true);

                    dataSet.setSliceSpace(3f);
                    dataSet.setIconsOffset(new MPPointF(0, 40));
                    dataSet.setSelectionShift(50f);

                    setColors();
                    dataSet.setColors(colors);

                    pieData = new PieData(dataSet);
                    pieData.setValueFormatter(new DefaultAxisValueFormatter(2));
                    pieData.setValueTextSize(22f);
                    pieData.setDrawValues(true);
                    pieData.setValueTextColor(Color.BLACK);

                    chart.setData(pieData);

                    dataSet.setDrawIcons(false);

                    dataSet.setSliceSpace(5f);
                    dataSet.setIconsOffset(new MPPointF(0, 0));
                    dataSet.setSelectionShift(10f);

                    chart.invalidate();

                }
            });
        }
    }

    private void setColors() {
        colors = new ArrayList<>();

        colors.add(Color.rgb(135, 190, 177));
        colors.add(Color.rgb(116, 159, 214));
        colors.add(Color.rgb(206, 139, 134));
        colors.add(Color.rgb(165, 225, 173));
        colors.add(Color.rgb(205, 140, 197));
        colors.add(Color.rgb(110, 150, 125));

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
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1800, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setDrawInside(true);
        l.setFormSize(15);
        l.setFormToTextSpace(5);
        l.setTextSize(20);
        l.setXEntrySpace(28f);
        l.setYEntrySpace(0f);
        l.setYOffset(40f);
        l.setWordWrapEnabled(true);

        chart.getLegend().setEnabled(false);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(14f);
        chart.setDrawEntryLabels(true);
    }
}