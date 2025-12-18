package com.example.passbook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.passbook.database.TransactionEntity;
import com.example.passbook.database.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private final TransactionRepository repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return repository.getAllTransactions();
    }

    public LiveData<Double> getTotalBalance() {
        return repository.getTotalBalance();
    }

    public void insert(TransactionEntity entity) {
        repository.insert(entity);
    }
}

