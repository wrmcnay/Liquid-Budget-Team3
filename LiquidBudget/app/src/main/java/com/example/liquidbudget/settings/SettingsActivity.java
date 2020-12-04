package com.example.liquidbudget.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.liquidbudget.R;
import com.example.liquidbudget.WelcomeActivity;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.JwtIdentity;
import zendesk.core.Zendesk;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;

public class SettingsActivity extends AppBaseActivity {
    GoogleSignInClient mGoogleSignInClient;

    protected void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Zendesk.INSTANCE.init(this, "https://liquidbudge.zendesk.com",
                "50ab7f8eda98e07ab9e83c06d976bcd470ea5ab2128fcec6",
                "mobile_sdk_client_91f28b51f26c0aac5e49");

        Identity identity = new JwtIdentity(account.getId());
        Zendesk.INSTANCE.setIdentity(identity);

        Support.INSTANCE.init(Zendesk.INSTANCE);

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
                HelpCenterActivity.builder()
                        .show(SettingsActivity.this);
            }
        });

        Button signOutUser = (Button) findViewById(R.id.signOut);
        signOutUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switch (view.getId()) {
                    case R.id.signOut:
                        signOut();
                        break;
                }

                Intent startIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(startIntent);
            }
        });

        if (account != null) {
            String userEmail = account.getEmail();
            TextView email = findViewById(R.id.emailEditText);
            email.setText("Email: " + userEmail);
        }
    }
}