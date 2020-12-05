package com.example.liquidbudget.GraphicalAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GraphActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_graphs_analysis);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            setContentView(R.layout.activity_graphs_analysis);
            String userName = account.getDisplayName();
            TextView homepagehello = findViewById(R.id.usersbudget);
            homepagehello.setText(getString(R.string.users_budget, userName));

            ViewPager pager = findViewById(R.id.pager);
            pager.setOffscreenPageLimit(4);

            PageAdapter a = new PageAdapter(getSupportFragmentManager());
            pager.setAdapter(a);

            Button button = findViewById(R.id.graphcurrentstandingbutton);
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PieChartCurrentStanding.class);
                    startActivity(intent);
                    return true;
                }
            });
        }
        else {
            setContentView(R.layout.activity_graphs_not_signed_in);
        }
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
                    f = PieChartBudget.newInstance();
                    break;
                case 1:
                    f = PieChartCategories.newInstance();
                    break;
                case 2:
                    f = PieChartExpenses.newInstance();
                    break;
                case 3:
                    f = PieChartIncomes.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

