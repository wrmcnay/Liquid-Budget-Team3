package com.example.liquidbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.data.viewmodels.UserAccountViewModel;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryActivity extends AppBaseActivity implements TutorialDialogue.TutorialDialogListener {
    public static final String CATEGORY_NAME = "com.example.liquidbudget.CATEGORY_NAME";

    private CategoryViewModel categoryViewModel;
    private final static int MY_REQUEST_CODE= 1;
    private String newCatName = "";
    private String newCatType = "";
    private Double newCatAmount = 0.0;
    private String googleID;
    TutorialDialogue d;
    private Integer numCategories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);

        // init view
        RecyclerView recyclerView = findViewById(R.id.lstCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null)
            googleID = googleAccount.getId();

        //Database
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if(googleAccount != null) {
            try {
                categoryViewModel.getAllCategoriesByGoogleId(googleID).observe(this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categoryList) {
                        adapter.setCategories(categoryList);
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Category myCategory = adapter.getCategoryAtPosition(position);
                        Toast.makeText(CategoryActivity.this, "Deleting " +
                                myCategory.getCategoryName(), Toast.LENGTH_LONG).show();

                        // Delete the income
                        categoryViewModel.deleteCategory(myCategory);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CategoryAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Category category = adapter.getCategoryAtPosition(position);
//                launchUpdateCategoryActivity(category);

                Intent viewActivity = new Intent(CategoryActivity.this, ViewCategoryActivity.class);
                String catName = category.getCategoryName();
                String catType = category.getCategoryType();
                Double catAmount = category.getCategoryAmount();

                viewActivity.putExtra("CategoryName", catName);
                viewActivity.putExtra("CategoryType", catType);
                viewActivity.putExtra("CategoryAmount", catAmount);
                viewActivity.putExtra("googleid", googleID);
                startActivity(viewActivity);
            }
        });

        try{
            numCategories = categoryViewModel.getNumCategories(googleID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton goToAddCategory = findViewById(R.id.goToAddCategory);
        goToAddCategory.setOnClickListener(new View.OnClickListener(){
            private final static String REQUEST_COLOR = "";
            @Override
            public void onClick(View view){
                if(numCategories==0){
                    Intent startIntent = new Intent(getApplicationContext(), PreloadCategories.class);
                    startActivity(startIntent);
                } else {
                    Intent startIntent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                    startActivityForResult(startIntent, MY_REQUEST_CODE);
                }
//
//                Intent startIntent = new Intent(getApplicationContext(), PreloadCategories.class);
//                startActivity(startIntent);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Categories");

        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        try {
            if(userAccountViewModel.getTutorialState(googleAccount.getId()) == 1) {
                launchTutorialDialog();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode==MY_REQUEST_CODE){
            if(data != null){
                newCatName = data.getStringExtra("Name");
                newCatType = data.getStringExtra("Type");
                newCatAmount = data.getDoubleExtra("Amount", 0.0);

                Category category = new Category(newCatName, newCatAmount, newCatType, " ", googleID);
                categoryViewModel.insertCategory(category);
                Toast.makeText(CategoryActivity.this, "Category Created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CategoryActivity.this, "Category could not be created", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void launchTutorialDialog() {
        d = new TutorialDialogue();
        d.setCurrentLayout(R.layout.tut4);
        d.setPositiveButtonText("OK");
        d.setNegativeButtonText("QUIT");
        d.show(getSupportFragmentManager(), "TutorialDialogFragment");
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        userAccountViewModel.setTutorialState(googleAccount.getId(), 2);

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UserAccountViewModel userAccountViewModel = new ViewModelProvider(this).get(UserAccountViewModel.class);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        try {
            if(userAccountViewModel.getTutorialState(googleID) == 2){
                try {
                    if(userAccountViewModel.getTutorialState(googleAccount.getId()) == 2){
                        d.setCurrentLayout(R.layout.tut5);
                        d.setPositiveButtonText("OK");
                        d.setNegativeButtonText("QUIT");
                        d.show(getSupportFragmentManager(), "TutorialDialogFragment");
                        userAccountViewModel.setTutorialState(googleAccount.getId(), 3);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }
}
