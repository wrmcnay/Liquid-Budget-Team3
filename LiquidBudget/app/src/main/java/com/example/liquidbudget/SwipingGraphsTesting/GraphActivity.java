package com.example.liquidbudget.SwipingGraphsTesting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;

public class GraphActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs_testing);

        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        PageAdapter a = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(a);

    }

    private class PageAdapter extends FragmentPagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch (pos) {
                case 0:
                    f = PieChartCategories.newInstance();
                    break;
                case 1:
                    f = PieChartExpenses.newInstance();
                    break;
                case 2:
                    f = PieChartIncomes.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

