package com.example.liquidbudget.GraphicalAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.liquidbudget.R;
import com.example.liquidbudget.TutorialDialogue;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.concurrent.ExecutionException;

public class GraphActivity extends AppBaseActivity implements TutorialDialogue.TutorialDialogListener {
    TutorialDialogue d;

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
            pager.setOffscreenPageLimit(3);

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

        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        try {
            if(userAccountViewModel.getTutorialState(account.getId()) == 3) {
                Log.d("hello", "yes");
                launchTutorialDialog();
            } else {
                Log.d("hello", "no");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    public void launchTutorialDialog() {
        d = new TutorialDialogue();
        d.setCurrentLayout(R.layout.tut6);
        d.setPositiveButtonText("OK");
        d.setNegativeButtonText("QUIT");
        d.show(getSupportFragmentManager(), "TutorialDialogFragment");
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        userAccountViewModel.setTutorialState(googleAccount.getId(), 4);
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

