package com.example.liquidbudget.GraphicalAnalysis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public abstract class SimpleFragment extends Fragment {

    private Typeface tf;
    protected Context context;

    private ArrayList<Object> colors;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public SimpleFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i + 1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tf);

        return d;
    }

    private final String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };

    private String getLabel(int i) {
        return mLabels[i];
    }

}