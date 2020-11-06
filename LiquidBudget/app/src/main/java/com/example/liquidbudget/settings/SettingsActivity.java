package com.example.liquidbudget.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SettingsActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button goToAccount = (Button) findViewById(R.id.goToAccount);
        goToAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(startIntent);
            }
        });

        Button goToNotifications = (Button) findViewById(R.id.goToNotifications);
        goToNotifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), NotificationsActivity.class);
                startActivity(startIntent);
            }
        });

        Button goToBanking = (Button) findViewById(R.id.goToBanking);
        goToBanking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), BankingActivity.class);
                startActivity(startIntent);
            }
        });

        Button goToHelp = (Button) findViewById(R.id.goToHelp);
        goToHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(startIntent);
            }
        });

        Button goToInvite = (Button) findViewById(R.id.goToInvite);
        goToInvite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), InviteActivity.class);
                startActivity(startIntent);
            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String userEmail = account.getEmail();
            TextView email = findViewById(R.id.emailEditText);
            email.setText("Email: " + userEmail);
        }
    }
}