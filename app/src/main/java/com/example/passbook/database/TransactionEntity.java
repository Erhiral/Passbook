package com.example.passbook.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public double amount;

    @NonNull
    public String type; // CREDIT or DEBIT

    public long timestamp;
    
    public String description;

    public TransactionEntity(String title, double amount, String type, long timestamp, String description) {
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
    }
    
    // For backward compatibility - marked with @Ignore for Room
    @androidx.room.Ignore
    public TransactionEntity(String title, double amount, String type, long timestamp) {
        this(title, amount, type, timestamp, "");
    }
}
