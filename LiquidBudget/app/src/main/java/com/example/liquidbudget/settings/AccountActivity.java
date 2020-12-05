package com.example.liquidbudget.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;

public class AccountActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Account");
        setContentView(R.layout.activity_account);

        Button editProfile = (Button) findViewById(R.id.changeAccountInfo);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://myaccount.google.com/personal-info"));
                startActivity(viewIntent);
            }
        });

        Button changePwd = (Button) findViewById(R.id.changePassword);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://myaccount.google.com/security"));
                startActivity(viewIntent);
            }
        });
    }
}