package com.example.liquidbudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Calendar;

public class ExtendedEducationActivity extends AppBaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_education);


        Button investingButton = findViewById(R.id.investingButton);
        Button taxesButton = findViewById(R.id.taxesButton);
        Button mortgagesButton = findViewById(R.id.mortgagesButton);
        Button studentLoanButton = findViewById(R.id.studentLoansButton);
        Button creditScoreButton = findViewById(R.id.creditScoreButton);
        Button retirementPlansButton = findViewById(R.id.retirementPlanButton);


        investingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.investopedia.com/articles/basics/11/3-s-simple-investing.asp"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        taxesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://apps.irs.gov/app/understandingTaxes/student/tax_tutorials.jsp"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mortgagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.rocketmortgage.com/learn/what-is-a-mortgage#:~:text=A%20mortgage%20is%20a%20type%20of%20loan%20that's%20used%20to,that%20they%20stop%20making%20payments"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        studentLoanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://blog.collegevine.com/borrowing-for-beginners-an-introduction-to-student-loans/#:~:text=Student%20loans%20can%20only%20be,College%20Expenses%20for%20more%20details"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        creditScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.cnbc.com/select/guide/credit-scores-for-beginners/#:~:text=A%20credit%20score%20is%20a,your%20credit%20score%20for%20free"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        retirementPlansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.nerdwallet.com/article/investing/retirement-planning-an-introduction"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });






    }






}
