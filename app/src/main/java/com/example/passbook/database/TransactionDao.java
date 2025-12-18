package com.example.passbook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(TransactionEntity entity);

    @Query("SELECT * FROM transaction_table ORDER BY timestamp DESC")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("SELECT SUM(CASE WHEN type='CREDIT' THEN amount ELSE -amount END) FROM transaction_table")
    LiveData<Double> getTotalBalance();
}