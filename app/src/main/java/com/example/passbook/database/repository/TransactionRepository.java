package com.example.passbook.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.passbook.database.AppDatabase;
import com.example.passbook.database.TransactionDao;
import com.example.passbook.database.TransactionEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private final TransactionDao dao;
    private final LiveData<List<TransactionEntity>> allTransactions;
    private final LiveData<Double> totalBalance;

    public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dao = db.transactionDao();
        allTransactions = dao.getAllTransactions();
        totalBalance = dao.getTotalBalance();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Double> getTotalBalance() {
        return totalBalance;
    }

    public void insert(TransactionEntity entity) {
        Executors.newSingleThreadExecutor().execute(() -> dao.insert(entity));
    }
}
