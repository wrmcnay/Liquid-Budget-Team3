package com.example.liquidbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liquidbudget.data.entities.Category;
import com.example.liquidbudget.data.entities.Income;
import com.example.liquidbudget.data.viewmodels.CategoryViewModel;
import com.example.liquidbudget.ui.DataAdapters.CategoryAdapter;
import com.example.liquidbudget.ui.main.AppBaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryActivity extends AppBaseActivity {
    public static final String CATEGORY_NAME = "com.example.liquidbudget.CATEGORY_NAME";

    public static final String EXTRA_DATA_UPDATE_CATEGORY_NAME = "extra_category_name_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_TYPE = "extra_category_type_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_AMOUNT = "extra_category_amount_to_update";
    public static final String EXTRA_DATA_UPDATE_CATEGORY_ID = "extra_data_cat_id";

    private CategoryViewModel categoryViewModel;
    private final static int MY_REQUEST_CODE= 1;
    private final static int UPDATE_CATEGORY_ACTIVITY_REQUEST_CODE = 2;
    private String newCatName = "";
    private String newCatType = "";
    private Double newCatAmount = 0.0;


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

        //Database
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                adapter.setCategories(categoryList);
            }
        });

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

                Intent viewActivity = new Intent(CategoryActivity.this, ViewCategoryActivity.class);
                String catName = category.getCategoryName();
                String catType = category.getCategoryType();
                Double catAmount = category.getCategoryAmount();

                viewActivity.putExtra("CategoryName", catName);
                viewActivity.putExtra("CategoryType", catType);
                viewActivity.putExtra("CategoryAmount", catAmount);
                startActivity(viewActivity);
            }
        });
//        recyclerView.setClickable(true);
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Intent viewActivity = new Intent(CategoryActivity.this, ViewCategoryActivity.class);
//                String catName = arg0.getItemAtPosition(position).toString();
//                viewActivity.putExtra("CategoryName", catName);
//                startActivity(viewActivity);
//            }
//        });

        FloatingActionButton goToAddCategory = findViewById(R.id.goToAddCategory);
        goToAddCategory.setOnClickListener(new View.OnClickListener(){
            private final static String REQUEST_COLOR = "";
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivityForResult(startIntent, MY_REQUEST_CODE);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Categories");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode==MY_REQUEST_CODE){
            if(data != null){
                newCatName = data.getStringExtra(AddCategoryActivity.EXTRA_CAT_NAME);
                newCatType = data.getStringExtra(AddCategoryActivity.EXTRA_CAT_TYPE);
                newCatAmount = data.getDoubleExtra(AddCategoryActivity.EXTRA_CAT_AMOUNT, 0.0);

                Category category = new Category(newCatName, newCatAmount, newCatType, " ");
                categoryViewModel.insertCategory(category);
                Toast.makeText(CategoryActivity.this, "Category Created", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Activity.RESULT_OK && requestCode==UPDATE_CATEGORY_ACTIVITY_REQUEST_CODE) {
                if (data != null) {
                    newCatName = data.getStringExtra(AddCategoryActivity.EXTRA_CAT_NAME);
                    newCatType = data.getStringExtra(AddCategoryActivity.EXTRA_CAT_TYPE);
                    newCatAmount = data.getDoubleExtra(AddCategoryActivity.EXTRA_CAT_AMOUNT, 0.0);

                    int id = data.getIntExtra(AddCategoryActivity.EXTRA_UPDATE_CATEGORY_ID, -1);

                    if (id != -1) {
                        Category category = new Category(id, newCatName, newCatAmount, newCatType, " ");
                        categoryViewModel.updateCategory(category);
                        Toast.makeText(CategoryActivity.this, "Category Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Category not able to update", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                Toast.makeText(CategoryActivity.this, "Category could not be created", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void launchUpdateCategoryActivity(Category category) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_NAME, category.getCategoryName());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_AMOUNT, category.getCategoryAmount());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_TYPE, category.getCategoryType());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY_ID, category.getCategoryID());
        startActivityForResult(intent, UPDATE_CATEGORY_ACTIVITY_REQUEST_CODE);
    }

}
