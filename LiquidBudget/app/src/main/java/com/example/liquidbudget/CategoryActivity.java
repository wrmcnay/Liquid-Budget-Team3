package com.example.liquidbudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.liquidbudget.data.Database.CategoryRepository;
import com.example.liquidbudget.data.Local.CategoryDataSource;
import com.example.liquidbudget.data.Local.CategoryDatabase;
import com.example.liquidbudget.data.model.Category;
import com.example.liquidbudget.ui.main.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryActivity extends AppBaseActivity {
    public static final String CATEGORY_NAME = "com.example.liquidbudget.CATEGORY_NAME";
    private ListView lstCategory;

    //adapter
    List<Category> categoryList = new ArrayList<>();
    ArrayAdapter adapter;
    Button goToAddCategory;

    //database
    private CompositeDisposable compositeDisposable;
    private CategoryRepository categoryRepository;

    private final static int MY_REQUEST_CODE= 1;
    private String newCatName = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode==MY_REQUEST_CODE){
                if(data != null)
                    newCatName = data.getStringExtra("Name");
            }
            Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {
                    Category category = new Category(newCatName, "red");
                    categoryRepository.insertCategory(category);
                    e.onComplete();
                }
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(CategoryActivity.this, "Category Added!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(CategoryActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main);

        //init
        compositeDisposable = new CompositeDisposable();

        // init view
        lstCategory = (ListView)findViewById(R.id.lstCategories);
        goToAddCategory = (Button)findViewById(R.id.goToAddCategory);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList);
        registerForContextMenu(lstCategory);
        lstCategory.setAdapter(adapter);

        //Database
        CategoryDatabase categoryDatabase = CategoryDatabase.getInstance(this); // Create Database
        categoryRepository = CategoryRepository.getInstance(CategoryDataSource.getInstance(categoryDatabase.categoryDAO()));

        //Load all data from Database
        loadData();

        goToAddCategory.setOnClickListener(new View.OnClickListener(){
            private final static String REQUEST_COLOR = "";
            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivityForResult(startIntent, MY_REQUEST_CODE);
            }
        });
    }

    private void loadData(){
        //Use RxJava
        Disposable disposable = categoryRepository.getAllCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Category>>() {
                       @Override
                       public void accept(List<Category> categories) throws Exception {
                           onGetAllCategoriesSuccess(categories);
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept(Throwable throwable) throws Exception {
                           Toast.makeText(CategoryActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
        compositeDisposable.add(disposable);
    }

    private void onGetAllCategoriesSuccess(List<Category> categories){
        categoryList.clear();
        categoryList.addAll(categories);
        adapter.notifyDataSetChanged();
    }

}


//
//        /**
//         * TODO: How can we dynamically assign buttons their own IDs?
//         */
//        Button category_test = (Button)findViewById(R.id.category_test);
//        category_test.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                openCategoryView();
//            }
//        });

//    public void openCategoryView() {
//        String testCategory = "Test Category";
//
//        Intent startIntent = new Intent(getApplicationContext(), ViewCategoryActivity.class);
//        startIntent.putExtra(CATEGORY_NAME, testCategory);
//        startActivity(startIntent);
//    }