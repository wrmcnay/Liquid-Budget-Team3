package com.example.liquidbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.entities.Income;

import java.util.List;

@Dao
public interface IncomeDAO {

    @Query("SELECT * FROM incomes WHERE incomeID=:incID")
    LiveData<Income> getIncomeById(int incID);

    @Query("SELECT * FROM incomes")
    LiveData<List<Income>> getAllIncomes();

    @Query("SELECT amount FROM incomes WHERE incomeID=:incID")
    LiveData<List<Double>> getAmountByIncID(int incID);

    @Query("SELECT * from incomes WHERE categoryName=:catName")
    LiveData<List<Income>> getIncomesByCategory(String catName);

    @Insert
    void insertIncome(Income... incomes);

    @Update
    void updateIncome(Income... incomes);

    @Delete
    void deleteIncome(Income incomes);

    @Query("DELETE FROM incomes")
    void deleteAllIncomes();


}
