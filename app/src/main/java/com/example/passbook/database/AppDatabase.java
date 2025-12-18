package com.example.passbook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TransactionEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TransactionDao transactionDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "expense_db"
            ).addMigrations(MIGRATION_1_2).build();
        }
        return instance;
    }

    // Migration from version 1 to 2: Add description column
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Add the new description column
            database.execSQL("ALTER TABLE transaction_table ADD COLUMN description TEXT DEFAULT ''");
        }
    };
}
