package com.example.liquidbudget.data.JacobDBWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.R;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserDisplayActivity extends AppBaseActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private UserAccountViewModel userAccountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);


        FloatingActionButton addUserBtn = findViewById(R.id.add_user_btn);
        addUserBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDisplayActivity.this, AddUserActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.User_Recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        userAccountViewModel.getAllUsers().observe(this, new Observer<List<UserAccount>>() {
            @Override
            public void onChanged(List<UserAccount> userAccounts) {
                adapter.setUsers(userAccounts);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String username = data.getStringExtra(AddUserActivity.EXTRA_USERNAME);
            String email = data.getStringExtra(AddUserActivity.EXTRA_EMAIL);
            int UID = data.getIntExtra(AddUserActivity.EXTRA_UID, 1);

            UserAccount user = new UserAccount(username, "password", "name", email);
            user.setUserID(UID);
            userAccountViewModel.insert(user);
            Toast.makeText(this,"User Added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }
}