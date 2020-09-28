package com.example.liquidbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liquidbudget.ui.login.LoginActivity;
import com.example.liquidbudget.ui.login.SignUpActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        signIn = (Button)findViewById(R.id.signInBtn);
        signUp = (Button)findViewById(R.id.signUpBtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignIn(v);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchSignUp(v);
            }
        });
    }

    public void launchSignIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void launchSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}