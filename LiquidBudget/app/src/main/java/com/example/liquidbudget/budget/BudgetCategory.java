package com.example.liquidbudget.budget;

public class BudgetCategory {
    private String mCatName;
    private int mCatImg;

    public BudgetCategory(String catName, int catImg) {
        mCatName = catName;
        mCatImg = catImg;
    }

    public String getCatName(){
        return mCatName;
    }

    public int getCatImg(){
        return mCatImg;
    }
}
