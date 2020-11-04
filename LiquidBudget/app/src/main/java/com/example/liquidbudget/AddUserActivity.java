package com.example.liquidbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liquidbudget.R;

public class AddUserActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "com.example.liquidbudget.EXTRA_USERNAME";
    public static final String EXTRA_EMAIL = "com.example.liquidbudget.EXTRA_EMAIL";
    public static final String EXTRA_UID = "com.example.liquidbudget.EXTRA_UID";

    private EditText UsernameEditText;
    private EditText EmailEditText;
    private EditText UIDEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        UsernameEditText = findViewById(R.id.username_edit_text);
        EmailEditText = findViewById(R.id.email_edit_text);
        UIDEditText = findViewById(R.id.UID_edit_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add User");
    }

    private void saveUser(){
        String username = UsernameEditText.getText().toString();
        String email = EmailEditText.getText().toString();
        int UID = Integer.parseInt(UIDEditText.getText().toString());

        if(username.trim().isEmpty() || email.trim().isEmpty() || UID == 0){
            Toast.makeText(this, "Please insert a username, email, and userID", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_USERNAME, username);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_UID, UID);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_user:
                saveUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}