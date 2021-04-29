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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BarChartHistory extends SimpleFragment {

    //private Context context;
    //private ExpenseViewModel expenseViewModel;
    //ArrayList<Integer> colors;
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

    BarChart chart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar_chart_history, container, false);

        //account = GoogleSignIn.getLastSignedInAccount(getContext());

        chart = v.findViewById(R.id.barGraph);
        chart.getDescription().setEnabled(false);

        formatChart();
        try {
            populateData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chart.invalidate();
        return v;
    }
    // title
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("History");
        s.setSpan(new RelativeSizeSpan(1.75f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }
    //put in data
    private void populateData() throws ExecutionException, InterruptedException {
        //y axis
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f, 0));
        barEntries.add(new BarEntry(44f, 1));
        barEntries.add(new BarEntry(44f, 2));
        barEntries.add(new BarEntry(44f, 3));
        barEntries.add(new BarEntry(44f, 4));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        //x-axis
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");

        BarData theData = new BarData((IBarDataSet) theDates, barDataSet);
        chart.setData(theData);

    }

    //formatting
    private void formatChart() {
        chart.getDescription().setEnabled(true);

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

*/
