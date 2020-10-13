package  com.example.liquidbudget.ui.login;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liquidbudget.MainActivity;
import com.example.liquidbudget.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        usernameEditText.setHintTextColor(Color.GRAY);
        passwordEditText.setHintTextColor(Color.GRAY);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              logIn(v);
            }
        });
    }

    private void logIn(View view){
        //  loadingProgressBar.setVisibility(View.VISIBLE);
        //loginViewModel.login(usernameEditText.getText().toString(),
        // passwordEditText.getText().toString());
        // TODO: PROCESS LOG-ON
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}