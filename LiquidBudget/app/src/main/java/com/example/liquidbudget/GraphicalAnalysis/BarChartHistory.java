package com.example.liquidbudget.GraphicalAnalysis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liquidbudget.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class BarChartHistory extends SimpleFragment {

    //private Context context;
    //private ExpenseViewModel expenseViewModel;
    BarChart chart;
    //ArrayList<Integer> colors;
    BarData barData;
    /*GoogleSignInAccount account;

    Intent expenseIntent;
    int expId;
    String expName;
    String catName;
    Double expAmount;
    String expDate;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;*/

    @NonNull
    public static Fragment newInstance() {
        return new BarChartHistory();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar_chart_history, container, false);

        //account = GoogleSignIn.getLastSignedInAccount(getContext());

        chart = v.findViewById(R.id.barGraph);
        chart.getDescription().setEnabled(false);

        //y axis
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(44f,1));
        barEntries.add(new BarEntry(44f,2));
        barEntries.add(new BarEntry(44f,3));
        barEntries.add(new BarEntry(44f,4));
        BarDataSet barDataSet = new BarDataSet (barEntries,"Dates");
        //x-axis
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");

        BarData barData = new BarData((IBarDataSet) theDates, barDataSet);
        chart.setData(barData);
        chart.invalidate();

        return v;
    }

}
        //expenseIntent = new Intent(getContext(), AddExpenseActivity.class);

        /*formatChart();
        try {
            populateData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                BarEntry pe = (BarEntry) e;
                String expenseName = pe.getLabel();

                try {
                    expenseViewModel.getAllExpensesByGoogleID(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
                        @Override
                        public void onChanged(List<Expense> expenses) {

                            for (Expense exp : expenses) {
                                if (exp.getExpenseName().equals(expenseName)) {
                                    expId = exp.getExpenseID();
                                    expName = exp.getExpenseName();
                                    catName = exp.getCategoryName();
                                    expAmount = exp.getAmount();
                                    expDate = exp.getDate();

                                    expenseIntent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_NAME, expName);
                                    expenseIntent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_CATEGORY, catName);
                                    expenseIntent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_AMOUNT, expAmount);
                                    expenseIntent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_DATE, expDate);
                                    expenseIntent.putExtra(EXTRA_DATA_UPDATE_EXPENSE_ID, expId);
                                    expenseIntent.putExtra("googleid", account.getId());
                                    startActivity(expenseIntent);
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
        if (account != null) {
            expenseViewModel.getAllExpensesByGoogleID(account.getId()).observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expenseList) {
                    entries.clear();
                    double am;
                    String name;

                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("MM");
                    date = dateFormat.format(calendar.getTime());

                    for (Expense exp : expenseList) {
                        if (exp.getDate().startsWith(date)) {
                            am = exp.getAmount();
                            name = exp.getExpenseName();
                            entries.add(new PieEntry((float) am, name));
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
                    pieData.setValueTextSize(15f);
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
    }*/
