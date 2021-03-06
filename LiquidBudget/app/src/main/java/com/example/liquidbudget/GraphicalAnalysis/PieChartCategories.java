package com.example.liquidbudget.GraphicalAnalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.liquidbudget.CategoryActivity;
import com.example.liquidbudget.R;
import com.example.liquidbudget.ViewCategoryActivity;
import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PieChartCategories extends SimpleFragment { //implements OnChartValueSelectedListener {

    private Context context;

    private CategoryViewModel categoryViewModel;
    private PieChart chart;
    ArrayList<PieEntry> entries;
    PieDataSet dataSet;
    ArrayList<Integer> colors;
    PieData pieData;
    GoogleSignInAccount account;

    int categoryId;
    String categoryType;
    Double categoryAmount;

    Intent categoryIntent;

    PopupWindow popUp;

    @NonNull
    public static Fragment newInstance() {
        return new PieChartCategories();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart_categories, container, false);

        chart = v.findViewById(R.id.pieChart1);
        chart.getDescription().setEnabled(false);

        account = GoogleSignIn.getLastSignedInAccount(getContext());

        categoryIntent = new Intent(getContext(), ViewCategoryActivity.class);

        formatChart();
        try {
            populateData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //chart.setOnTouchListener(null);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                PieEntry pe = (PieEntry) e;
                String categoryName = pe.getLabel();

                try {
                    categoryViewModel.getAllCategoriesByGoogleId(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                        @Override
                        public void onChanged(List<Category> categories) {

                            for (Category cat : categories) {
                                if(!(cat.getCategoryType().equals("Income"))) {
                                    if (cat.getCategoryName().equals(categoryName)) {
                                        categoryId = cat.getCategoryID();
                                        categoryType = cat.getCategoryType();
                                        categoryAmount = cat.getCategoryAmount();

                                        categoryIntent.putExtra("CategoryId", categoryId);
                                        categoryIntent.putExtra("CategoryName", categoryName);
                                        categoryIntent.putExtra("CategoryType", categoryType);
                                        categoryIntent.putExtra("CategoryAmount", categoryAmount);
                                        categoryIntent.putExtra("googleid", account.getId());
                                        startActivity(categoryIntent);
                                    }
                                }
                            }
                        }
                    });

                } catch (ExecutionException executionException) {
                    executionException.printStackTrace();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.invalidate();

        //popUp = new PopupWindow(this);

        return v;
    }



    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("CATEGORIES");
        s.setSpan(new RelativeSizeSpan(1.55f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    private void populateData() throws ExecutionException, InterruptedException {
        entries = new ArrayList<>();
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if (account != null) {
            categoryViewModel.getAllCategoriesByGoogleId(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categoryList) {
                    entries.clear();
                    String name;
                    double amount;
                    for (Category cat : categoryList) {
                        if (!(cat.getCategoryType()).equals("Income")) {
                            name = cat.getCategoryName();
                            amount = cat.getCategoryAmount();
                            entries.add(new PieEntry((float) amount, name));
                        }
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
                    pieData.setValueTextSize(13f);
                    pieData.setDrawValues(true);
                    pieData.setValueTextColor(Color.BLACK);

                    chart.setData(pieData);

                    dataSet.setDrawIcons(true);

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

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
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
        chart.setTouchEnabled(true);

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

    /*public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry) e;
        String categoryName = pe.getLabel();

        try {
            categoryViewModel.getAllCategoriesByGoogleId(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {

                    for (Category cat : categories) {
                        if (cat.getCategoryName().equals(categoryName)) {
                            categoryId = cat.getCategoryID();
                            categoryAmount = cat.getCategoryAmount();
                            categoryType = cat.getCategoryType();
                        }
                    }
                }
            });

        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        categoryIntent.putExtra("CategoryId", categoryId);
        categoryIntent.putExtra("CategoryName", categoryName);
        categoryIntent.putExtra("CategoryType", categoryType);
        categoryIntent.putExtra("CategoryAmount", categoryAmount);
        categoryIntent.putExtra("googleid", account.getId());
        startActivity(categoryIntent);
    }

    @Override
    public void onNothingSelected() {

    }

     */
}
