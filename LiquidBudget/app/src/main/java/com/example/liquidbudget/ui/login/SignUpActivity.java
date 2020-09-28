package com.example.liquidbudget.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liquidbudget.MainActivity;
import com.example.liquidbudget.R;

public class SignUpActivity extends AppCompatActivity {

    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.signUpBtn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
    }

    private void signUp(View view){
        // TODO: PROCESS Sign-Up
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}