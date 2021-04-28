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

    @Query("SELECT * FROM incomes WHERE googleID=:googleID")
    LiveData<List<Income>> getAllIncomesByGoogleId(String googleID);

    @Query("SELECT amount FROM incomes WHERE incomeID=:incID")
    LiveData<List<Double>> getAmountByIncID(int incID);

    @Query("SELECT * from incomes WHERE categoryName=:catName AND googleID=:googleID")
    LiveData<List<Income>> getIncomesByCategory(String catName, String googleID);

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM incomes WHERE categoryName=:catName AND googleID=:googleID")
    Double getSumByCategory(String catName, String googleID);

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM incomes")
    Double getSumTotal();

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM incomes WHERE googleID=:googleID")
    Double getSumTotalForGoogleID(String googleID);

    @Query("SELECT CAST(SUM(amount) as DOUBLE) FROM incomes WHERE googleID=:googleID AND date LIKE :month || '%'")
    Double getMonthSumTotalForGoogleID(String googleID, String month);

    @Query("UPDATE incomes SET categoryName=:newName WHERE categoryName=:oldName")
    void updateCategoryName(String oldName, String newName);

    @Insert
    void insertIncome(Income... incomes);

    @Update
    void updateIncome(Income... incomes);

    @Delete
    void deleteIncome(Income incomes);

    @Query("DELETE FROM incomes")
    void deleteAllIncomes();


}
