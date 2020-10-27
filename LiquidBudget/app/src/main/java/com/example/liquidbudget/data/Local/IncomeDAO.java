package com.example.liquidbudget.data.Local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liquidbudget.data.model.Income;

import java.util.List;

@Dao
public interface IncomeDAO {

    @Query("SELECT * FROM incomes WHERE incomeID=:incID")
    LiveData<Income> getIncomeById(int incID);

    @Query("SELECT * FROM incomes")
    LiveData<List<Income>> getAllIncomes();

    @Insert
    void insertIncome(Income... incomes);

    @Update
    void updateIncome(Income... incomes);

    @Delete
    void deleteIncome(Income incomes);

    @Query("DELETE FROM incomes")
    void deleteAllIncomes();


}
