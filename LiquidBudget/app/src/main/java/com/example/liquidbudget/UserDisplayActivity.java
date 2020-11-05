package com.example.liquidbudget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.UserAccount;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.DataAdapters.UserAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String name = data.getStringExtra(AddUserActivity.EXTRA_NAME);
            int UID = data.getIntExtra(AddUserActivity.EXTRA_UID, 1);

            UserAccount user = new UserAccount(username, name, email);
            user.setUserID(UID);
            userAccountViewModel.insert(user);
            Toast.makeText(this, "User Added!", Toast.LENGTH_SHORT).show();
            storeUserOnline(user);
        } else {
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeUserOnline(UserAccount user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usr = new HashMap<>();
        usr.put("Username", user.getUserName());
        usr.put("ID", user.getUserID());


        CollectionReference users = db.collection("users");
        users.document("" + user.getUserID())
                .set(usr)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UserAdd", "Successfully added user");
                    }
                });

        String user2Name = "User 2 " + user.getName();
        String user2UserName = "User 2 " + user.getUserName();
        String user2Email = "User 2 " + user.getEmail();
        int user2UID = user.getUserID() + 1;
        UserAccount user2 = new UserAccount(user2UserName, user2Name, user2Email);
        user2.setUserID(user2UID);
        userAccountViewModel.insert(user2);

        //Some other code you could use 
        /*db.collection("users")
                .add(usr)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("UserAdd", "DocumentSnapshot added w/ ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("UserAdd", "Error adding document", e);
                    }
                });*/

        /*db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //success to-do: parse thru collection
                        } else {
                            //fail to-do
                        }
                    }
                });*/
    }
}