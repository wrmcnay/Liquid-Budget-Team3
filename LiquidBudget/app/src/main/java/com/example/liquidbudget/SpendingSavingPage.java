package com.example.liquidbudget;

import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.IncomeViewModel;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpendingSavingPage extends AppBaseActivity implements OnChartValueSelectedListener, TutorialDialogue.TutorialDialogListener {

    private IncomeViewModel incomeViewModel;
    private PieChart chart;
    ArrayList<PieEntry> entries;
    PieDataSet dataSet;
    ArrayList<Integer> colors;
    PieData pieData;
    TutorialDialogue d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_saving_page);

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

    }

    public void launchTutorialDialog() {
        d = new TutorialDialogue();
        d.setCurrentLayout(R.layout.tut4);
        d.setPositiveButtonText("OK");
        d.setNegativeButtonText("QUIT");
        d.show(getSupportFragmentManager(), "TutorialDialogFragment");
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        userAccountViewModel.setTutorialState(googleAccount.getId(), 4);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("INCOMES");
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
            public void onChanged(List<Income> incomesList) {
                entries.clear();
                double am;
                String name;
                for (Income inc : incomesList) {
                    am = inc.getAmount();
                    name = inc.getIncomeName();
                    entries.add(new PieEntry((float) am, name));
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        try {
            if(userAccountViewModel.getTutorialState(account.getId()) == 4){
                d.dismiss();
                d.setCurrentLayout(R.layout.tut7);
                d.setPositiveButtonText("OK");
                d.setNegativeButtonText("QUIT");
                d.show(getSupportFragmentManager(), "TutorialDialogFragment");
                userAccountViewModel.setTutorialState(account.getId(), 5);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}



